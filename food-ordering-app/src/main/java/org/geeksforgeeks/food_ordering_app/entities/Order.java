package org.geeksforgeeks.food_ordering_app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "total_amount")
    private Double totalAmount;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private OrderStatus status = OrderStatus.ORDERED;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

}
