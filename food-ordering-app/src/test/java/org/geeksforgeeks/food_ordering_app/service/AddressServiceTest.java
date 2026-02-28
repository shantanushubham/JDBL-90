package org.geeksforgeeks.food_ordering_app.service;

import org.geeksforgeeks.food_ordering_app.auth.UserContextHandler;
import org.geeksforgeeks.food_ordering_app.entities.Address;
import org.geeksforgeeks.food_ordering_app.entities.Customer;
import org.geeksforgeeks.food_ordering_app.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserContextHandler userContextHandler;

    @InjectMocks
    private AddressService addressService;

    @Test
    void createAddress_setsCustomerAndSaves() {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(customerId);
        Address address = new Address();

        org.geeksforgeeks.food_ordering_app.model.CustomUserDetails customUserDetails = org.mockito.Mockito
                .mock(org.geeksforgeeks.food_ordering_app.model.CustomUserDetails.class);
        when(customUserDetails.getCustomer()).thenReturn(customer);
        when(userContextHandler.getCustomUserDetails()).thenReturn(customUserDetails);
        when(addressRepository.save(address)).thenReturn(address);

        Address result = addressService.createAddress(address);

        assertSame(address, result);
        verify(addressRepository).save(address);
    }

    @Test
    void getAddressById_returnsFromRepository() {
        UUID id = UUID.randomUUID();
        Address address = new Address();
        when(addressRepository.findById(id)).thenReturn(address);

        assertSame(address, addressService.getAddressById(id));
    }

    @Test
    void getAllAddressesForCustomer_returnsList() {
        UUID customerId = UUID.randomUUID();
        List<Address> list = List.of(new Address());
        when(addressRepository.findAllByCustomer(customerId)).thenReturn(list);

        assertSame(list, addressService.getAllAddressesForCustomer(customerId));
    }

    @Test
    void updateAddress_updatesAndSaves() {
        UUID id = UUID.randomUUID();
        Address existing = new Address();
        Address details = new Address();
        details.setStreet("s");
        details.setCity("c");
        details.setState("st");
        details.setZipCode("z");
        details.setCountry("co");
        when(addressRepository.findById(id)).thenReturn(existing);
        when(addressRepository.save(existing)).thenReturn(existing);

        Address result = addressService.updateAddress(id, details);

        assertSame(existing, result);
        verify(addressRepository).save(existing);
    }

    @Test
    void deleteAddress_findsAndDeletes() {
        UUID id = UUID.randomUUID();
        Address address = new Address();
        when(addressRepository.findById(id)).thenReturn(address);

        addressService.deleteAddress(id);

        verify(addressRepository).delete(address);
    }
}
