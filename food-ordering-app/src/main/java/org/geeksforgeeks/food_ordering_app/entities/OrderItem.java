package org.geeksforgeeks.food_ordering_app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(name = "quantity")
    private Integer quantity;

}
