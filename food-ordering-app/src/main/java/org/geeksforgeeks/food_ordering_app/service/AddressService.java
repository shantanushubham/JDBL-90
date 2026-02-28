package org.geeksforgeeks.food_ordering_app.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.auth.UserContextHandler;
import org.geeksforgeeks.food_ordering_app.entities.Address;
import org.geeksforgeeks.food_ordering_app.entities.Customer;
import org.geeksforgeeks.food_ordering_app.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserContextHandler userContextHandler;

    @Transactional
    public Address createAddress(Address address) {
        Customer customer = this.userContextHandler.getCustomUserDetails().getCustomer();
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
