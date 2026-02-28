package org.geeksforgeeks.food_ordering_app.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
