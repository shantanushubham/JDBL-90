package org.geeksforgeeks.food_ordering_app.repository;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.Customer;
import org.geeksforgeeks.food_ordering_app.exceptions.NotFoundException;
import org.geeksforgeeks.food_ordering_app.repository.jpa.CustomerJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    public Customer save(Customer customer) {
        return customerJpaRepository.save(customer);
    }

    public Customer findById(UUID id) {
        return customerJpaRepository.findById(id).orElseThrow(() -> new NotFoundException(Customer.class, "id", id));
    }

    public Optional<Customer> findByEmail(String email) {
        return customerJpaRepository.findByEmail(email);
    }

    public List<Customer> findAll() {
        return customerJpaRepository.findAll();
    }

    public void delete(Customer customer) {
        customerJpaRepository.delete(customer);
    }

    public void deleteById(UUID id) {
        customerJpaRepository.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return customerJpaRepository.existsById(id);
    }
}
