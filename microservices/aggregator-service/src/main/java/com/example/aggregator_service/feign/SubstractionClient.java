package com.example.aggregator_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "substraction-client", url = "http://localhost:3000")
public interface SubstractionClient {

    @GetMapping("/subtract")
    Double getDifference(@RequestParam double num1, @RequestParam double num2);
}
