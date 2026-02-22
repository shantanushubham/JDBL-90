package org.geeksforgeeks.food_ordering_app.repository.jpa;

import org.geeksforgeeks.food_ordering_app.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemJpaRepository extends JpaRepository<OrderItem, UUID> {
    List<OrderItem> findByOrderId(UUID orderId);

    /**
     * Returns the most ordered menu item for a restaurant in the given time window (DB-only).
     * Result: [0]=menu_item_id, [1]=name, [2]=price, [3]=description, [4]=total_quantity (sum).
     */
    @Query(value = """
        SELECT mi.id, mi.name, mi.price, mi.description, SUM(oi.quantity) AS total_quantity
        FROM order_items oi
        JOIN orders o ON oi.order_id = o.id
        JOIN menu_items mi ON oi.menu_item_id = mi.id
        WHERE o.restaurant_id = :restaurantId AND o.order_date > :since
        GROUP BY mi.id, mi.name, mi.price, mi.description
        ORDER BY total_quantity DESC
        LIMIT 1
        """, nativeQuery = true)
    List<Object[]> findMostOrderedItemInLast30DaysNative(
            @Param("restaurantId") UUID restaurantId,
            @Param("since") LocalDateTime since);
}
