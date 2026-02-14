package org.geeksforgeeks.food_ordering_app.repository;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.Restaurant;
import org.geeksforgeeks.food_ordering_app.repository.jpa.RestaurantJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RestaurantRepository {

    private final RestaurantJpaRepository restaurantJpaRepository;

    public Restaurant save(Restaurant restaurant) {
        return restaurantJpaRepository.save(restaurant);
    }

    public Optional<Restaurant> findById(UUID id) {
        return restaurantJpaRepository.findById(id);
    }

    public List<Restaurant> findAll() {
        return restaurantJpaRepository.findAll();
    }

    public void delete(Restaurant restaurant) {
        restaurantJpaRepository.delete(restaurant);
    }

    public void deleteById(UUID id) {
        restaurantJpaRepository.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return restaurantJpaRepository.existsById(id);
    }
}
