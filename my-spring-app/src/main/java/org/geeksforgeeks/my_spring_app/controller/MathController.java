package org.geeksforgeeks.my_spring_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {

    @GetMapping("/add")
    public int add(@RequestParam int a, @RequestParam int b) {
        System.out.println(System.getenv("name"));
        return a + b;
    }

    @GetMapping("/subtract")
    public int subtract(@RequestParam int a, @RequestParam int b) {
        return a - b;
    }
}
