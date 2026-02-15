package org.geeksforgeeks.food_ordering_app.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.Address;
import org.geeksforgeeks.food_ordering_app.entities.Customer;
import org.geeksforgeeks.food_ordering_app.exceptions.NotFoundException;
import org.geeksforgeeks.food_ordering_app.repository.AddressRepository;
import org.geeksforgeeks.food_ordering_app.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public Address createAddress(UUID customerId, Address address) {
        Customer customer = this.customerRepository.findById(customerId);
        address.setCustomer(customer);
        return this.addressRepository.save(address);
    }

    public Address getAddressById(UUID id) {
        return this.addressRepository.findById(id);
    }

    public List<Address> getAllAddressesForCustomer(UUID customerId) {
        return this.addressRepository.findAllByCustomer(customerId);
    }

    @Transactional
    public Address updateAddress(UUID id, Address addressDetails) {
        Address address = addressRepository.findById(id);

        address.setStreet(addressDetails.getStreet());
        address.setCity(addressDetails.getCity());
        address.setState(addressDetails.getState());
        address.setZipCode(addressDetails.getZipCode());
        address.setCountry(addressDetails.getCountry());

        return addressRepository.save(address);
    }

    @Transactional
    public void deleteAddress(UUID id) {
        Address address = addressRepository.findById(id);
        addressRepository.delete(address);
    }
}
