package org.geeksforgeeks.my_spring_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "order"
    )
    // Homework to read about cascadeType & generation strategy
    private List<OrderItem> itemList;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "order_date", nullable = false)
    private Date orderDate = new Date();

    private Double orderTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status = OrderStatus.ORDERED;
}
