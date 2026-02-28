package org.geeksforgeeks.food_ordering_app.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.dto.request.CustomerCreateRequest;
import org.geeksforgeeks.food_ordering_app.entities.Customer;
import org.geeksforgeeks.food_ordering_app.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Customer createCustomer(CustomerCreateRequest customerCreateRequest) {
        Customer customer = Customer.builder()
                .firstName(customerCreateRequest.getFirstName())
                .lastName(customerCreateRequest.getLastName())
                .email(customerCreateRequest.getEmail())
                .mobile(customerCreateRequest.getMobile())
                .password(this.passwordEncoder.encode(customerCreateRequest.getPassword()))
                .build();
        
        return this.customerRepository.save(customer);
    }

    public Customer getCustomerById(UUID id) {
        return this.customerRepository.findById(id);
    }

    public Customer getCustomerByEmail(String email) {
        return this.customerRepository.findByEmail(email);
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Transactional
    public Customer updateCustomer(UUID id, Customer customerDetails) {
        Customer customer = this.customerRepository.findById(id);
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setEmail(customerDetails.getEmail());
        customer.setMobile(customerDetails.getMobile());
        customer.setPassword(customerDetails.getPassword());
        
        return this.customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(UUID id) {
        Customer customer = this.customerRepository.findById(id) ;
        this.customerRepository.delete(customer);
    }
}
