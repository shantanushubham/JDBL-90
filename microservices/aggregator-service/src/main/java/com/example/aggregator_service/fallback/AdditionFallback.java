package com.example.aggregator_service.fallback;

import com.example.aggregator_service.feign.AdditionClient;

import org.springframework.stereotype.Component;

@Component
public class AdditionFallback implements AdditionClient {

    @Override
    public Double getSum(double num1, double num2) {
        return 0.0D;
    }
}
