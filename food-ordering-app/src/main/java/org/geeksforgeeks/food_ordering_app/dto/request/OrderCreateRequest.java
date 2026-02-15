package org.geeksforgeeks.food_ordering_app.dto.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderCreateRequest {

    private UUID customerId;
    private UUID restaurantId;
    private List<OrderItemCreateRequest> itemQuantityList;
    private UUID addressId;
}

