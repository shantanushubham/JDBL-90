package org.geeksforgeeks.food_ordering_app.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.Restaurant;
import org.geeksforgeeks.food_ordering_app.repository.jpa.RestaurantJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantJpaRepository restaurantJpaRepository;

    @Transactional
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantJpaRepository.save(restaurant);
    }

    public Optional<Restaurant> getRestaurantById(UUID id) {
        return restaurantJpaRepository.findById(id);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantJpaRepository.findAll();
    }

    @Transactional
    public Restaurant updateRestaurant(UUID id, Restaurant restaurantDetails) {
        Restaurant restaurant = restaurantJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
        
        restaurant.setName(restaurantDetails.getName());
        
        return restaurantJpaRepository.save(restaurant);
    }

    @Transactional
    public void deleteRestaurant(UUID id) {
        Restaurant restaurant = restaurantJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
        restaurantJpaRepository.delete(restaurant);
    }
}
