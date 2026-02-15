package org.geeksforgeeks.food_ordering_app.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<?> type, Object field, Object value) {
        super(String.format("Cannot find object %s with field: %s=%s", type, field, value));
        log.info("Cannot find object {} with field: {}={}", type, field, value);
    }
}
