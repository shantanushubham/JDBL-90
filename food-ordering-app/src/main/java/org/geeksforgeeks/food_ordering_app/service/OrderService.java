package org.geeksforgeeks.food_ordering_app.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.Order;
import org.geeksforgeeks.food_ordering_app.repository.jpa.OrderJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;

    @Transactional
    public Order createOrder(Order order) {
        return orderJpaRepository.save(order);
    }

    public Optional<Order> getOrderById(UUID id) {
        return orderJpaRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderJpaRepository.findAll();
    }

    public List<Order> getOrdersByCustomerId(UUID customerId) {
        return orderJpaRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersByRestaurantId(UUID restaurantId) {
        return orderJpaRepository.findByRestaurantId(restaurantId);
    }

    @Transactional
    public Order updateOrder(UUID id, Order orderDetails) {
        Order order = orderJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        order.setStatus(orderDetails.getStatus());
        order.setTotalAmount(orderDetails.getTotalAmount());
        
        return orderJpaRepository.save(order);
    }

    @Transactional
    public void deleteOrder(UUID id) {
        Order order = orderJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderJpaRepository.delete(order);
    }
}
