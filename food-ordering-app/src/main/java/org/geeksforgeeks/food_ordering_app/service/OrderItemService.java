package org.geeksforgeeks.food_ordering_app.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.OrderItem;
import org.geeksforgeeks.food_ordering_app.repository.jpa.OrderItemJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemJpaRepository orderItemRepository;

    @Transactional
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public Optional<OrderItem> getOrderItemById(UUID id) {
        return orderItemRepository.findById(id);
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public List<OrderItem> getOrderItemsByOrderId(UUID orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Transactional
    public OrderItem updateOrderItem(UUID id, OrderItem orderItemDetails) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + id));
        
        orderItem.setQuantity(orderItemDetails.getQuantity());

        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public void deleteOrderItem(UUID id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + id));
        orderItemRepository.delete(orderItem);
    }
}
