package org.geeksforgeeks.food_ordering_app.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.Address;
import org.geeksforgeeks.food_ordering_app.repository.jpa.AddressJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressJpaRepository addressJpaRepository;

    @Transactional
    public Address createAddress(Address address) {
        return addressJpaRepository.save(address);
    }

    public Optional<Address> getAddressById(UUID id) {
        return addressJpaRepository.findById(id);
    }

    public List<Address> getAllAddresses() {
        return addressJpaRepository.findAll();
    }

    @Transactional
    public Address updateAddress(UUID id, Address addressDetails) {
        Address address = addressJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + id));
        
        address.setStreet(addressDetails.getStreet());
        address.setCity(addressDetails.getCity());
        address.setState(addressDetails.getState());
        address.setZipCode(addressDetails.getZipCode());
        address.setCountry(addressDetails.getCountry());
        
        return addressJpaRepository.save(address);
    }

    @Transactional
    public void deleteAddress(UUID id) {
        Address address = addressJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + id));
        addressJpaRepository.delete(address);
    }
}
