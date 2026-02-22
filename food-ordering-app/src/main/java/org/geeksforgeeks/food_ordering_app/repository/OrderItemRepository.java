package org.geeksforgeeks.food_ordering_app.repository;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.OrderItem;
import org.geeksforgeeks.food_ordering_app.repository.jpa.OrderItemJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {

    private final OrderItemJpaRepository orderItemJpaRepository;

    public OrderItem save(OrderItem orderItem) {
        return orderItemJpaRepository.save(orderItem);
    }

    public Optional<OrderItem> findById(UUID id) {
        return orderItemJpaRepository.findById(id);
    }

    public List<OrderItem> findAll() {
        return orderItemJpaRepository.findAll();
    }

    public List<OrderItem> findByOrderId(UUID orderId) {
        return orderItemJpaRepository.findByOrderId(orderId);
    }

    public void delete(OrderItem orderItem) {
        orderItemJpaRepository.delete(orderItem);
    }

    public void deleteById(UUID id) {
        orderItemJpaRepository.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return orderItemJpaRepository.existsById(id);
    }

    /**
     * DB-only: most ordered menu item for restaurant in the given time window.
     * Returns one row: [menu_item_id, name, price, description, total_quantity].
     */
    public List<Object[]> findMostOrderedItemInLast30DaysNative(UUID restaurantId, LocalDateTime since) {
        return orderItemJpaRepository.findMostOrderedItemInLast30DaysNative(restaurantId, since);
    }
}
