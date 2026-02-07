package org.geeksforgeeks.my_spring_app.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Order {

    @EmbeddedId
    private OrderKey orderKey;

}

@Embeddable
class OrderKey {

    private Long orderId;
    private Long itemId;
}
