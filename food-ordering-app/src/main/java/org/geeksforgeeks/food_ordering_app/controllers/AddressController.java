package org.geeksforgeeks.food_ordering_app.controllers;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.Address;
import org.geeksforgeeks.food_ordering_app.exceptions.NotFoundException;
import org.geeksforgeeks.food_ordering_app.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{customerId}")
    public ResponseEntity<?> createAddress(
            @PathVariable UUID customerId,
            @RequestBody Address address) {
        try {
            Address createdAddress = this.addressService.createAddress(customerId, address);
            return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable UUID id) {
        try {
            Address address = this.addressService.getAddressById(id);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getAllAddressesForCustomer(@PathVariable UUID customerId) {
        try {
            List<Address> addresses = this.addressService.getAllAddressesForCustomer(customerId);
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable UUID id, @RequestBody Address addressDetails) {
        try {
            Address updatedAddress = this.addressService.updateAddress(id, addressDetails);
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable UUID id) {
        try {
            this.addressService.deleteAddress(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
