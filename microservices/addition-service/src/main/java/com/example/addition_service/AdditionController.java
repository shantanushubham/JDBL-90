package com.example.addition_service;

import com.example.addition_service.events.TestEvent;
import com.example.addition_service.producers.TestEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AdditionController {

    private final TestEventProducer testEventProducer;

    @GetMapping("/add")
    public double add(@RequestParam double num1, @RequestParam double num2) {
        if (num1 < 0 || num2 < 0) {
            throw new RuntimeException();
        }
        return num1 + num2;
    }

    @GetMapping("/produce")
    public void produce(@RequestParam String name) {
        TestEvent testEvent = new TestEvent(name);
        this.testEventProducer.sendTestEvent(testEvent);
    }
}
