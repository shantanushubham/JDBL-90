package org.geeksforgeeks.food_ordering_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "customers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

}
