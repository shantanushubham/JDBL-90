package org.geeksforgeeks.food_ordering_app.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;

}
