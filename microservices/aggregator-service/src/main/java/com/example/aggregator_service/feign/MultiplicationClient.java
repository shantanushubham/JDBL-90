package com.example.aggregator_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "multiplication-client", url = "http://localhost:8000")
public interface MultiplicationClient {

    @GetMapping("/multiply")
    Double getProduct(@RequestParam double num1, @RequestParam double num2);
}
