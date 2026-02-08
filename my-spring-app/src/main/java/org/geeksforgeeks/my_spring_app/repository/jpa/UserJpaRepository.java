package org.geeksforgeeks.my_spring_app.repository.jpa;

import org.geeksforgeeks.my_spring_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID> {
}
