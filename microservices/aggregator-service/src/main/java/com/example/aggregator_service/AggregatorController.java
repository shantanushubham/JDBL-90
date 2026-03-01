package com.example.aggregator_service;

import com.example.aggregator_service.feign.AdditionClient;
import com.example.aggregator_service.feign.MultiplicationClient;
import com.example.aggregator_service.feign.SubstractionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class AggregatorController {

    private final AdditionClient additionClient;
    private final SubstractionClient substractionClient;
    private final MultiplicationClient multiplicationClient;

    @Autowired
    public AggregatorController(AdditionClient additionClient, SubstractionClient substractionClient, MultiplicationClient multiplicationClient) {
        this.additionClient = additionClient;
        this.substractionClient = substractionClient;
        this.multiplicationClient = multiplicationClient;
    }

    @GetMapping("/aggregate")
    public Map<String, Object> aggregate(@RequestParam double num1, @RequestParam double num2) {
        CompletableFuture<Double> addFuture = CompletableFuture.supplyAsync(() ->
                this.additionClient.getSum(num1, num2));

        CompletableFuture<Double> subtractFuture = CompletableFuture.supplyAsync(() ->
                this.substractionClient.getDifference(num1, num2));

        CompletableFuture<Double> multiplyFuture = CompletableFuture.supplyAsync(() ->
                this.multiplicationClient.getProduct(num1, num2));

        CompletableFuture.allOf(addFuture, subtractFuture, multiplyFuture).join();

        Double sum = addFuture.join();
        Double difference = subtractFuture.join();
        Double product = multiplyFuture.join();
        Double quotient = num2 != 0 ? num1 / num2 : null;

        Map<String, Object> response = new HashMap<>();
        response.put("sum", sum);
        response.put("difference", difference);
        response.put("product", product);
        response.put("quotient", quotient);
        return response;
    }
}
