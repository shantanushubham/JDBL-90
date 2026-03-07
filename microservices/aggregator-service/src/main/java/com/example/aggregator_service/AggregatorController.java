package com.example.aggregator_service;

import com.example.aggregator_service.feign.MultiplicationClient;
import com.example.aggregator_service.feign.SubstractionClient;
import com.example.aggregator_service.service.AdditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AggregatorController {

    private final AdditionService additionService;
    private final SubstractionClient substractionClient;
    private final MultiplicationClient multiplicationClient;

    @Autowired
    public AggregatorController(AdditionService additionService, SubstractionClient substractionClient, MultiplicationClient multiplicationClient) {
        this.additionService = additionService;
        this.substractionClient = substractionClient;
        this.multiplicationClient = multiplicationClient;
    }

    @GetMapping("/aggregate")
    public Map<String, Object> aggregate(@RequestParam double num1, @RequestParam double num2) {
        Double sum = additionService.getSum(num1, num2);
//        Double difference = substractionClient.getDifference(num1, num2);
//        Double product = multiplicationClient.getProduct(num1, num2);
        Double quotient = num2 != 0 ? num1 / num2 : null;

        Map<String, Object> response = new HashMap<>();
        response.put("sum", sum);
        response.put("difference", -1);
        response.put("product", -1);
        response.put("quotient", quotient);
        return response;
    }
}
