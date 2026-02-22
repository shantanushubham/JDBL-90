package org.geeksforgeeks.food_ordering_app.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MathService {

    public int addTwoNumbers(int a, int b) {
        return a + b;
    }

    public int mulTwoNumbers(int a, int b) {
        return a * b;
    }

    @Async
    public CompletableFuture<String> process() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Proceed";
        });
    }
}
