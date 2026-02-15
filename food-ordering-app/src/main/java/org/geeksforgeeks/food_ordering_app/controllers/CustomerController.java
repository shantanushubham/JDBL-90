package org.geeksforgeeks.food_ordering_app.controllers;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.dto.request.CustomerCreateRequest;
import org.geeksforgeeks.food_ordering_app.entities.Customer;
import org.geeksforgeeks.food_ordering_app.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerCreateRequest customerCreateRequest) {
        try {
            Customer createdCustomer = this.customerService.createCustomer(customerCreateRequest);
            return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable UUID id) {
        try {
            Customer customer = this.customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
