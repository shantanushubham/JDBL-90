package org.geeksforgeeks.food_ordering_app.repository.jpa;

import org.geeksforgeeks.food_ordering_app.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<Restaurant, UUID> {
}
