package org.geeksforgeeks.my_spring_app.service;

import org.geeksforgeeks.my_spring_app.entities.User;
import org.geeksforgeeks.my_spring_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        return this.userRepository.saveUser(user);
    }

    public List<User> getAllUsers() {
        return this.userRepository.getAllUsers();
    }

    public User getUserById(UUID id) {
        return this.userRepository.getUserById(id);
    }

    public void deleteUser(UUID id) {
        this.userRepository.deleteUser(id);
    }
}
