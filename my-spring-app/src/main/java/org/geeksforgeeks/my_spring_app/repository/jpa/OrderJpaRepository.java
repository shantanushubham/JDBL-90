package org.geeksforgeeks.my_spring_app.repository.jpa;

import org.geeksforgeeks.my_spring_app.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUser_Id(UUID id);
}
