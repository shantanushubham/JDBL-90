package org.geeksforgeeks.food_ordering_app.dto.response;

import lombok.Data;

@Data
public class OrderResponseItem {

    private String itemName;
    private int quantity;
    private double amount;

}
