package org.geeksforgeeks.my_spring_app.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateOrderRequest {

    private UUID userId;
    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        private int itemId;
        private int quantity;
    }
}