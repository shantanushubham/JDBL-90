package org.geeksforgeeks.food_ordering_app.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.Customer;
import org.geeksforgeeks.food_ordering_app.repository.jpa.CustomerJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerJpaRepository customerJpaRepository;

    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerJpaRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(UUID id) {
        return customerJpaRepository.findById(id);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerJpaRepository.findByEmail(email);
    }

    public List<Customer> getAllCustomers() {
        return customerJpaRepository.findAll();
    }

    @Transactional
    public Customer updateCustomer(UUID id, Customer customerDetails) {
        Customer customer = customerJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setEmail(customerDetails.getEmail());
        customer.setMobile(customerDetails.getMobile());
        customer.setPassword(customerDetails.getPassword());
        
        return customerJpaRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(UUID id) {
        Customer customer = customerJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        customerJpaRepository.delete(customer);
    }
}
