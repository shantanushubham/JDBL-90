package org.geeksforgeeks.food_ordering_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

}
