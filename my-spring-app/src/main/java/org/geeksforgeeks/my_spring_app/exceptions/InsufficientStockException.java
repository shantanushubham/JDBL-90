package org.geeksforgeeks.my_spring_app.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String itemName, int quantityRequested, int quantityAvailable) {
        super(String.format("Insufficient stock for item '%s'. Requested: %d, Available: %d",
                itemName, quantityRequested, quantityAvailable));
        log.error("Insufficient stock for item '{}'. Requested: {}, Available: {}",
                itemName, quantityRequested, quantityAvailable);
    }
}
