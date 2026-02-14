package org.geeksforgeeks.my_spring_app.service;

import org.geeksforgeeks.my_spring_app.dto.CreateOrderRequest;
import org.geeksforgeeks.my_spring_app.entities.Item;
import org.geeksforgeeks.my_spring_app.entities.Order;
import org.geeksforgeeks.my_spring_app.entities.OrderItem;
import org.geeksforgeeks.my_spring_app.entities.OrderStatus;
import org.geeksforgeeks.my_spring_app.exceptions.InsufficientStockException;
import org.geeksforgeeks.my_spring_app.exceptions.NotFoundException;
import org.geeksforgeeks.my_spring_app.repository.ItemRepository;
import org.geeksforgeeks.my_spring_app.repository.OrderRepository;
import org.geeksforgeeks.my_spring_app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Order addOrder(Order order) {
        this.setOrderTotal(order);
        return this.orderRepository.saveOrder(order);
    }

    @Transactional
    public Order addOrderFromRequest(CreateOrderRequest createOrderRequest) {
        Order order = new Order();
        List<Integer> itemIds = createOrderRequest.getItems().stream().map(el -> el.getItemId()).toList();
        List<Item> itemList = this.itemRepository.getItemsByIds(itemIds);
        List<OrderItem> orderItemList = createOrderRequest.getItems().stream().map(el -> {
            Item item = itemList.stream().filter(o -> o.getId() == el.getItemId()).toList().get(0);
            if (item.getCount() < el.getQuantity()) {
                throw new InsufficientStockException(item.getName(), el.getQuantity(), item.getCount());
            }
            item.changeCount(el.getQuantity() * -1);
            return new OrderItem(null, order, item, el.getQuantity());
        }).toList();

        order.setItemList(orderItemList);
        order.setUser(this.userRepository.getUserById(createOrderRequest.getUserId()));
        order.setStatus(OrderStatus.ORDERED);
        Order savedOrder =  this.addOrder(order);
        this.itemRepository.saveAll(itemList);
        return savedOrder;
    }

    public List<Order> getOrderByUserId(UUID userId) {
        return this.orderRepository.getOrdersByUser(userId);
    }

    public Order getOrderById(UUID id) {
        return this.orderRepository.getOrderById(id);
    }

    public void deleteOrder(UUID id) {
        this.orderRepository.deleteOrder(id);
    }

    public List<Order> getAllOrders() {
        return this.orderRepository.getAllOrders();
    }

    private void setOrderTotal(Order order) {
        double total = order.getItemList().stream().mapToDouble(el -> el.getQuantity() * el.getItem().getPrice())
                .sum();
        order.setOrderTotal(total);
    }

    @Transactional
    public synchronized Order cancelOrder(UUID orderId) {
        Order order = this.orderRepository.getOrderById(orderId);
        
        if (order == null) {
            throw new NotFoundException(Order.class, "id", orderId);
        }
        
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled");
        }
        
        // Restore inventory for each item in the order
        List<Item> itemsToUpdate = order.getItemList().stream()
                .map(orderItem -> {
                    Item item = orderItem.getItem();
                    item.changeCount(orderItem.getQuantity()); // Add back the quantity
                    return item;
                })
                .toList();
        
        // Update order status
        order.setStatus(OrderStatus.CANCELLED);
        
        // Save changes
        this.itemRepository.saveAll(itemsToUpdate);
        return this.orderRepository.saveOrder(order);
    }
}

