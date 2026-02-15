package org.geeksforgeeks.food_ordering_app.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemCreateRequest {

    private UUID menuItemId;
    private int quantity;

}
