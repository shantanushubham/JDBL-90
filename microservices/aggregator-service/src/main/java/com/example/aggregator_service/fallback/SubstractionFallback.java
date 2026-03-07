package com.example.aggregator_service.fallback;

import com.example.aggregator_service.feign.SubstractionClient;
import org.springframework.stereotype.Component;

@Component
public class SubstractionFallback implements SubstractionClient {

    @Override
    public Double getDifference(double num1, double num2) {
        return 0.0D;
    }
}
