package org.geeksforgeeks.food_ordering_app.repository;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.Order;
import org.geeksforgeeks.food_ordering_app.exceptions.NotFoundException;
import org.geeksforgeeks.food_ordering_app.repository.jpa.OrderJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    public Order findById(UUID id) {
        return this.orderJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Order.class, "id", id));
    }

    public List<Order> findAll() {
        return orderJpaRepository.findAll();
    }

    public List<Order> findByCustomerId(UUID customerId) {
        return orderJpaRepository.findByCustomerId(customerId);
    }

    public List<Order> findByRestaurantId(UUID restaurantId) {
        return orderJpaRepository.findByRestaurantId(restaurantId);
    }

    public List<Order> findByRestaurantIdAndOrderDateAfter(UUID restaurantId, LocalDateTime since) {
        return orderJpaRepository.findByRestaurantIdAndOrderDateAfter(restaurantId, since);
    }

    public void delete(Order order) {
        orderJpaRepository.delete(order);
    }

    public void deleteById(UUID id) {
        orderJpaRepository.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return orderJpaRepository.existsById(id);
    }
}
