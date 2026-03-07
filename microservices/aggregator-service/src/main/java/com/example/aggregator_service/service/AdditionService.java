package com.example.aggregator_service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class AdditionService {

    private final WebClient webClient;

    public AdditionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @Retry(name = "addition-service", fallbackMethod = "getSumFallback")
    public Double getSum(double num1, double num2) {
        log.info("Attempting to call addition-service");
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/add")
                        .queryParam("num1", num1)
                        .queryParam("num2", num2)
                        .build())
                .retrieve()
                .bodyToMono(Double.class)
                .block();
    }

    public Double getSumFallback(double num1, double num2, Throwable ex) {
        log.warn("Addition service is down after retries, returning fallback. Reason: {}", ex.getMessage());
        return 0.0D;
    }
}
