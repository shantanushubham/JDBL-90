package org.geeksforgeeks.food_ordering_app.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.auth.UserContextHandler;
import org.geeksforgeeks.food_ordering_app.dto.request.OrderCreateRequest;
import org.geeksforgeeks.food_ordering_app.dto.request.OrderItemCreateRequest;
import org.geeksforgeeks.food_ordering_app.dto.response.MostOrderedItemResponse;
import org.geeksforgeeks.food_ordering_app.entities.*;
import org.geeksforgeeks.food_ordering_app.repository.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final MenuItemRepository menuItemRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserContextHandler userContextHandler;

    public Order createOrder(OrderCreateRequest orderCreateRequest) {
        Order order = new Order();
        Customer customer = this.userContextHandler.getCustomUserDetails().getCustomer();
        Restaurant restaurant = this.restaurantRepository.findById(orderCreateRequest.getRestaurantId());
        Address address = this.addressRepository.findById(orderCreateRequest.getAddressId());

        List<UUID> menuItemIdList = orderCreateRequest.getItemQuantityList().stream()
                .map(OrderItemCreateRequest::getMenuItemId)
                .toList();
        List<MenuItem> menuItemList = this.menuItemRepository.findAllByIdList(menuItemIdList);

        List<OrderItem> orderItemList = orderCreateRequest.getItemQuantityList().stream()
                .map(orderItemCreateRequest -> {
                    UUID menuItemId = orderItemCreateRequest.getMenuItemId();
                    int quantity = orderItemCreateRequest.getQuantity();

                    List<MenuItem> filteredList = menuItemList.stream()
                            .filter(el -> el.getId().equals(menuItemId))
                            .toList();

                    return OrderItem.builder()
                            .menuItem(filteredList.get(0))
                            .quantity(quantity)
                            .order(order)
                            .build();
                }).toList();

        order.setOrderItems(orderItemList);
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(address);

        return this.createOrder(order);

    }

    @Transactional
    public Order createOrder(Order order) {
        this.setOrderTotal(order);
        return orderRepository.save(order);
    }

    public Order getOrderById(UUID id) {
        return this.orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByCustomerId(UUID customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersByRestaurantId(UUID restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    /**
     * Returns the most ordered menu item for the given restaurant in the last 30 days,
     * with the total quantity ordered.
     */
    @Cacheable("restaurant")
    public Optional<MostOrderedItemResponse> getMostOrderedItemInLast30Days(UUID restaurantId) {
        LocalDateTime since = LocalDateTime.now().minusDays(30);
        List<Order> orders = this.orderRepository.findByRestaurantIdAndOrderDateAfter(restaurantId, since);

        // Count quantities by menu item ID
        Map<UUID, Integer> quantityByMenuItem = new HashMap<>();
        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                UUID menuItemId = orderItem.getMenuItem().getId();
                int quantity = orderItem.getQuantity() != null ? orderItem.getQuantity() : 0;
                quantityByMenuItem.put(menuItemId, quantityByMenuItem.getOrDefault(menuItemId, 0) + quantity);
            }
        }

        // Find menu item with maximum quantity
        UUID topMenuItemId = null;
        int maxQuantity = 0;
        for (Map.Entry<UUID, Integer> entry : quantityByMenuItem.entrySet()) {
            if (entry.getValue() > maxQuantity) {
                maxQuantity = entry.getValue();
                topMenuItemId = entry.getKey();
            }
        }

        if (topMenuItemId == null) {
            return Optional.empty();
        }

        Optional<MenuItem> menuItem = this.menuItemRepository.findById(topMenuItemId);
        if (menuItem.isEmpty()) {
            return Optional.empty();
        }
        MostOrderedItemResponse response = MostOrderedItemResponse.from(menuItem.get(), maxQuantity);
        this.redisTemplate.opsForValue().set("restaurant:" + restaurantId, response);
        return Optional.of(response);
    }

    /**
     * Same as getMostOrderedItemInLast30Days but computed entirely in the DB (single native query).
     * For demonstration: one round-trip, no in-memory aggregation.
     */
    public Optional<MostOrderedItemResponse> getMostOrderedItemInLast30DaysFromDb(UUID restaurantId) {
        LocalDateTime since = LocalDateTime.now().minusDays(30);
        List<Object[]> rows = orderItemRepository.findMostOrderedItemInLast30DaysNative(restaurantId, since);
        if (rows == null || rows.isEmpty()) {
            return Optional.empty();
        }
        MostOrderedItemResponse response = MostOrderedItemResponse.fromNativeRow(rows.get(0));
        return response != null ? Optional.of(response) : Optional.empty();
    }

    @Transactional
    public Order updateOrder(UUID id, Order orderDetails) {
        Order order = orderRepository.findById(id);

        order.setStatus(orderDetails.getStatus());
        order.setTotalAmount(orderDetails.getTotalAmount());

        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(UUID id) {
        Order order = orderRepository.findById(id);
        orderRepository.delete(order);
    }

    private void setOrderTotal(Order order) {
        double total = order.getOrderItems().stream()
                .mapToDouble(el -> el.getQuantity() * el.getMenuItem().getPrice())
                .sum();
        order.setTotalAmount(total);
    }
}
