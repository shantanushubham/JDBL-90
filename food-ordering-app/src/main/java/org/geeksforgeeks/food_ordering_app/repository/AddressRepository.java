package org.geeksforgeeks.food_ordering_app.repository;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.Address;
import org.geeksforgeeks.food_ordering_app.repository.jpa.AddressJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AddressRepository {

    private final AddressJpaRepository addressJpaRepository;

    public Address save(Address address) {
        return addressJpaRepository.save(address);
    }

    public Optional<Address> findById(UUID id) {
        return addressJpaRepository.findById(id);
    }

    public List<Address> findAll() {
        return addressJpaRepository.findAll();
    }

    public void delete(Address address) {
        addressJpaRepository.delete(address);
    }

    public void deleteById(UUID id) {
        addressJpaRepository.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return addressJpaRepository.existsById(id);
    }
}
