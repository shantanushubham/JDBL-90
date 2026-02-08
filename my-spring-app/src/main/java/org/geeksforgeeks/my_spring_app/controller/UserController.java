package org.geeksforgeeks.my_spring_app.controller;

import lombok.extern.slf4j.Slf4j;
import org.geeksforgeeks.my_spring_app.entities.User;
import org.geeksforgeeks.my_spring_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        log.info("UserController initialized with UserService: {}", userService);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Request to create user: {}", user);
        User createdUser = userService.addUser(user);
        log.info("User created successfully with ID: {}", createdUser.getId()); // Assuming getId() exists, checking
                                                                                // User.java again... it has @Getter so
                                                                                // yes. Actually wait.
        // User.java has `private UUID id;` and `@Getter`. But is the method `getId()`
        // or `getUserId()`?
        // In User.java: `private UUID id;` -> `getId()`. Wait, let me double check
        // User.java content previously read.
        // Line 26: private UUID id; -> getId()
        // Line 24 in User.java (from Step 31) was `private UUID userId;`.
        // Line 26 in User.java (from Step 106) was `private UUID id;`.
        // Wait, I saw two versions of User.java?
        // Step 31 showed: `private UUID userId;` on line 24.
        // Step 106 showed: `private UUID id;` on line 26.
        // Ah, maybe user changed it? Or I misread?
        // Let me check Step 106 response again carefully.
        // Step 106: `26: private UUID id;`.
        // Step 31: `24: private UUID userId;`.
        // It seems the user or I changed it?
        // Step 106 is the latest read. So it uses `id`.
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Request to fetch all users");
        List<User> users = userService.getAllUsers();
        log.info("Retrieved {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        log.info("Request to fetch user with ID: {}", id);
        User user = userService.getUserById(id);
        log.info("User retrieved: {}", user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        log.info("Request to delete user with ID: {}", id);
        userService.deleteUser(id);
        log.info("User deleted successfully: {}", id);
        return ResponseEntity.noContent().build();
    }
}
