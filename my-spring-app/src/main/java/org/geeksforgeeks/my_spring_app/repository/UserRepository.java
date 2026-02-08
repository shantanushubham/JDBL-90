package org.geeksforgeeks.my_spring_app.repository;

import org.geeksforgeeks.my_spring_app.entities.User;
import org.geeksforgeeks.my_spring_app.exceptions.NotFoundException;
import org.geeksforgeeks.my_spring_app.repository.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Autowired
    public UserRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public User getUserById(UUID id) {
        return this.userJpaRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, "id", id));
    }

    public User saveUser(User user) {
        return this.userJpaRepository.save(user);
    }

    public List<User> getAllUsers() {
        return this.userJpaRepository.findAll();
    }

    public void deleteUser(UUID id) {
        this.userJpaRepository.deleteById(id);
    }
}
