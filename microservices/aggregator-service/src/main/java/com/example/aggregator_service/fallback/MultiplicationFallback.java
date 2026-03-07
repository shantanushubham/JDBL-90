package com.example.aggregator_service.fallback;

import com.example.aggregator_service.feign.MultiplicationClient;
import org.springframework.stereotype.Component;

@Component
public class MultiplicationFallback implements MultiplicationClient {

    @Override
    public Double getProduct(double num1, double num2) {
        return 0.0D;
    }
}
