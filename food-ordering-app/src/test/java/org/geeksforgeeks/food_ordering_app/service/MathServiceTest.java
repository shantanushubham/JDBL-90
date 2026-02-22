package org.geeksforgeeks.food_ordering_app.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class MathServiceTest {

    @Autowired
    private MathService mathService;

    @Test
    void test_addTwoNumbers_happy() {
        int a = 5;
        int b = 10;
        int result = mathService.addTwoNumbers(a, b);

        Assertions.assertEquals(15, result);
    }

    @Test
    void test_mulTwoNumbers_happy() {
        int a = 5;
        int b = 10;
        int result = mathService.mulTwoNumbers(a, b);

        Assertions.assertEquals(50, result);
    }

    @Test
    void should_complete() throws Exception {
        CompletableFuture<String> future = mathService.process();
        String result = future.get();
        Assertions.assertEquals("Proceed", result);
    }

    @Test
    void should_complete_within_time() throws Exception {
        CompletableFuture<String> future = mathService.process();
        String result = future.get(3, TimeUnit.SECONDS);
        Assertions.assertEquals("Proceed", result);
    }
}
