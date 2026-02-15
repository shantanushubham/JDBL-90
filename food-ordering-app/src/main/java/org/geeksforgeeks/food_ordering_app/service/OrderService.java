package org.geeksforgeeks.food_ordering_app.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.dto.request.OrderCreateRequest;
import org.geeksforgeeks.food_ordering_app.dto.request.OrderItemCreateRequest;
import org.geeksforgeeks.food_ordering_app.entities.*;
import org.geeksforgeeks.food_ordering_app.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final MenuItemRepository menuItemRepository;

    public Order createOrder(OrderCreateRequest orderCreateRequest) {
        Order order = new Order();
        Customer customer = this.customerRepository.findById(orderCreateRequest.getCustomerId());
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

    public Optional<Order> getOrderById(UUID id) {
        return orderRepository.findById(id);
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

    @Transactional
    public Order updateOrder(UUID id, Order orderDetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setStatus(orderDetails.getStatus());
        order.setTotalAmount(orderDetails.getTotalAmount());

        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

    private void setOrderTotal(Order order) {
        double total = order.getOrderItems().stream()
                .mapToDouble(el -> el.getQuantity() * el.getMenuItem().getPrice())
                .sum();
        order.setTotalAmount(total);
    }
}
