package com.example.addition_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEvent {

    private UUID id;
    private String name;

    public TestEvent(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }
}
