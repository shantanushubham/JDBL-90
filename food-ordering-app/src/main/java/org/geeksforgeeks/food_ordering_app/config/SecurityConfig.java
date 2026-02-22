package org.geeksforgeeks.food_ordering_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.authorizeHttpRequests(auth ->
                auth.anyRequest()
                        .authenticated()
        ).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("my_app_user")
                .password("{noop}my_app_password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
