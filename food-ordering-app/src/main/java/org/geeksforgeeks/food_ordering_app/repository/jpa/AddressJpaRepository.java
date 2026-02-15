package org.geeksforgeeks.food_ordering_app.repository.jpa;

import org.geeksforgeeks.food_ordering_app.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressJpaRepository extends JpaRepository<Address, UUID> {

    List<Address> findByCustomer_Id(UUID id);
}
