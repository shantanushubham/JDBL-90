package org.geeksforgeeks.food_ordering_app.auth;

import org.geeksforgeeks.food_ordering_app.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserContextHandler {

    public CustomUserDetails getCustomUserDetails() {
        Authentication authentication = this.getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
