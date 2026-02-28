package org.geeksforgeeks.food_ordering_app.service;

import org.geeksforgeeks.food_ordering_app.dto.request.CustomerCreateRequest;
import org.geeksforgeeks.food_ordering_app.entities.Customer;
import org.geeksforgeeks.food_ordering_app.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCustomer_savesAndReturns() {
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setMobile("123");
        request.setPassword("pass");
        Customer saved = Customer.builder().id(UUID.randomUUID()).build();
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encoded");
        when(customerRepository.save(any(Customer.class))).thenReturn(saved);

        Customer result = customerService.createCustomer(request);

        assertSame(saved, result);
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void getCustomerById_returnsFromRepository() {
        UUID id = UUID.randomUUID();
        Customer customer = Customer.builder().id(id).build();
        when(customerRepository.findById(id)).thenReturn(customer);

        Customer result = customerService.getCustomerById(id);

        assertSame(customer, result);
    }

    // @Test
    // void getCustomerByEmail_returnsOptional() {
    // when(customerRepository.findByEmail("a@b.com")).thenThrow(NotFoundException.class);
    //
    // assertThrows(NotFoundException,
    // customerService.getCustomerByEmail("a@b.com").isEmpty());
    // }

    @Test
    void getAllCustomers_returnsList() {
        List<Customer> list = List.of(Customer.builder().build());
        when(customerRepository.findAll()).thenReturn(list);

        assertSame(list, customerService.getAllCustomers());
    }

    @Test
    void updateCustomer_updatesAndSaves() {
        UUID id = UUID.randomUUID();
        Customer existing = Customer.builder().id(id).build();
        Customer details = Customer.builder().firstName("J").lastName("D").build();
        when(customerRepository.findById(id)).thenReturn(existing);
        when(customerRepository.save(existing)).thenReturn(existing);

        Customer result = customerService.updateCustomer(id, details);

        assertSame(existing, result);
        verify(customerRepository).save(existing);
    }

    @Test
    void deleteCustomer_findsAndDeletes() {
        UUID id = UUID.randomUUID();
        Customer customer = Customer.builder().id(id).build();
        when(customerRepository.findById(id)).thenReturn(customer);

        customerService.deleteCustomer(id);

        verify(customerRepository).delete(customer);
    }
}
