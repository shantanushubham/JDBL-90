package org.geeksforgeeks.digitalwallet.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private String password;
    private LocalDate dob;
    private String pan;
}
