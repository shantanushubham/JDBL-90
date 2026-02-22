package org.geeksforgeeks.food_ordering_app.repository.jpa;

import org.geeksforgeeks.food_ordering_app.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerId(UUID customerId);
    List<Order> findByRestaurantId(UUID restaurantId);

    @Query("SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId AND o.orderDate > :since")
    List<Order> findByRestaurantIdAndOrderDateAfter(@Param("restaurantId") UUID restaurantId, @Param("since") LocalDateTime since);
}
