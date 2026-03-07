package com.example.aggregator_service.feign;

import com.example.aggregator_service.fallback.MultiplicationFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "multiplication-client", url = "http://localhost:8000", fallback = MultiplicationFallback.class)
public interface MultiplicationClient {

    @GetMapping("/multiply")
    Double getProduct(@RequestParam double num1, @RequestParam double num2);
}
