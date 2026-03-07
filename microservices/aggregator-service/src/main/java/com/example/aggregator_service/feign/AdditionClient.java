package com.example.aggregator_service.feign;

import com.example.aggregator_service.fallback.AdditionFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "addition-client", url = "http://localhost:8080", fallback = AdditionFallback.class)
public interface AdditionClient {

    @GetMapping("/add")
    Double getSum(@RequestParam double num1, @RequestParam double num2);
}
