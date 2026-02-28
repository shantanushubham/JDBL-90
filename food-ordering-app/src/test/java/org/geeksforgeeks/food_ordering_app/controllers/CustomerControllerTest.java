package org.geeksforgeeks.food_ordering_app.controllers;

import org.geeksforgeeks.food_ordering_app.dto.request.CustomerCreateRequest;
import org.geeksforgeeks.food_ordering_app.entities.Customer;
import org.geeksforgeeks.food_ordering_app.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        customerController = new CustomerController(customerService);
    }

    @Test
    void createCustomer_returnsCreated() {
        Customer customer = Customer.builder().id(UUID.randomUUID()).build();
        when(customerService.createCustomer(any(CustomerCreateRequest.class))).thenReturn(customer);

        ResponseEntity<?> result = customerController.createCustomer(new CustomerCreateRequest());

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void createCustomer_returns500_onException() {
        when(customerService.createCustomer(any(CustomerCreateRequest.class))).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = customerController.createCustomer(new CustomerCreateRequest());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getCustomer_returnsOk() {
        UUID id = UUID.randomUUID();
        org.geeksforgeeks.food_ordering_app.model.CustomUserDetails customUserDetails = org.mockito.Mockito
                .mock(org.geeksforgeeks.food_ordering_app.model.CustomUserDetails.class);
        org.geeksforgeeks.food_ordering_app.entities.Customer customerMock = new org.geeksforgeeks.food_ordering_app.entities.Customer();
        customerMock.setId(id);
        when(customUserDetails.getCustomer()).thenReturn(customerMock);
        when(customerService.getCustomerById(id)).thenReturn(Customer.builder().id(id).build());

        ResponseEntity<?> result = customerController.getCustomer(customUserDetails);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getCustomer_returns500_onException() {
        UUID id = UUID.randomUUID();
        org.geeksforgeeks.food_ordering_app.model.CustomUserDetails customUserDetails = org.mockito.Mockito
                .mock(org.geeksforgeeks.food_ordering_app.model.CustomUserDetails.class);
        org.geeksforgeeks.food_ordering_app.entities.Customer customerMock = new org.geeksforgeeks.food_ordering_app.entities.Customer();
        customerMock.setId(id);
        when(customUserDetails.getCustomer()).thenReturn(customerMock);
        when(customerService.getCustomerById(id)).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = customerController.getCustomer(customUserDetails);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}
