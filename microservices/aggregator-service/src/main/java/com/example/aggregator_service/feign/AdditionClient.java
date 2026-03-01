package com.example.aggregator_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "addition-client", url = "http://localhost:8080")
public interface AdditionClient {

    @GetMapping("/add")
    Double getSum(@RequestParam double num1, @RequestParam double num2);
}
