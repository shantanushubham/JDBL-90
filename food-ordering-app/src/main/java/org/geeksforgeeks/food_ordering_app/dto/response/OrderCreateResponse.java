package org.geeksforgeeks.food_ordering_app.dto.response;

import lombok.Data;
import org.geeksforgeeks.food_ordering_app.entities.Address;
import org.geeksforgeeks.food_ordering_app.entities.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderCreateResponse {

    private UUID orderId;
    private OrderStatus orderStatus;
    private LocalDateTime createdDate;
    private String restaurantName;
    private String customerName;
    private List<OrderResponseItem> items;
    private double orderTotal;
    private Address address;
}
