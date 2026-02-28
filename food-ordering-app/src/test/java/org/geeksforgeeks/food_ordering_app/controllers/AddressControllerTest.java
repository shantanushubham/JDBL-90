package org.geeksforgeeks.food_ordering_app.controllers;

import org.geeksforgeeks.food_ordering_app.entities.Address;
import org.geeksforgeeks.food_ordering_app.entities.Customer;
import org.geeksforgeeks.food_ordering_app.exceptions.NotFoundException;
import org.geeksforgeeks.food_ordering_app.model.CustomUserDetails;
import org.geeksforgeeks.food_ordering_app.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressService addressService;

    private AddressController addressController;

    @BeforeEach
    void setUp() {
        addressController = new AddressController(addressService);
    }

    @Test
    void createAddress_returnsCreated() {
        Address address = new Address();
        when(addressService.createAddress(any(Address.class))).thenReturn(address);

        ResponseEntity<?> result = addressController.createAddress(new Address());

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void createAddress_returns500_onException() {
        when(addressService.createAddress(any(Address.class))).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = addressController.createAddress(new Address());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getAddressById_returnsOk() {
        UUID id = UUID.randomUUID();
        when(addressService.getAddressById(id)).thenReturn(new Address());

        ResponseEntity<?> result = addressController.getAddressById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAddressById_returns404_onNotFoundException() {
        UUID id = UUID.randomUUID();
        when(addressService.getAddressById(id)).thenThrow(new NotFoundException(Address.class, "id", id));

        ResponseEntity<?> result = addressController.getAddressById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getAddressById_returns500_onException() {
        UUID id = UUID.randomUUID();
        when(addressService.getAddressById(id)).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = addressController.getAddressById(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getAllAddressesForCustomer_returnsOk() {
        UUID customerId = UUID.randomUUID();
        CustomUserDetails customUserDetails = Mockito
                .mock(CustomUserDetails.class);
        Customer customer = new Customer();
        customer.setId(customerId);
        when(customUserDetails.getCustomer()).thenReturn(customer);

        ResponseEntity<?> result = addressController.getAllAddressesForCustomer(customUserDetails);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void updateAddress_returnsOk() {
        UUID id = UUID.randomUUID();
        Address address = new Address();
        when(addressService.updateAddress(eq(id), any(Address.class))).thenReturn(address);

        ResponseEntity<?> result = addressController.updateAddress(id, new Address());

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void updateAddress_returns404_onRuntimeException() {
        UUID id = UUID.randomUUID();
        when(addressService.updateAddress(eq(id), any(Address.class))).thenThrow(new RuntimeException("not found"));

        ResponseEntity<?> result = addressController.updateAddress(id, new Address());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void updateAddress_returns404_whenServiceThrowsRuntimeException() {
        UUID id = UUID.randomUUID();
        when(addressService.updateAddress(eq(id), any(Address.class))).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = addressController.updateAddress(id, new Address());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteAddress_returnsNoContent() {
        UUID id = UUID.randomUUID();

        ResponseEntity<?> result = addressController.deleteAddress(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void deleteAddress_returns404_onRuntimeException() {
        UUID id = UUID.randomUUID();
        doThrow(new RuntimeException("not found")).when(addressService).deleteAddress(id);

        ResponseEntity<?> result = addressController.deleteAddress(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteAddress_returns404_whenServiceThrowsRuntimeException() {
        UUID id = UUID.randomUUID();
        doThrow(new RuntimeException("err")).when(addressService).deleteAddress(id);

        ResponseEntity<?> result = addressController.deleteAddress(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
