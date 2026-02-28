package org.geeksforgeeks.food_ordering_app.repository;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.Restaurant;
import org.geeksforgeeks.food_ordering_app.exceptions.NotFoundException;
import org.geeksforgeeks.food_ordering_app.repository.jpa.RestaurantJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RestaurantRepository {

    private final RestaurantJpaRepository restaurantRepository;

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant findById(UUID id) {
        return restaurantRepository.findById(id).orElseThrow(() ->
                new NotFoundException(Restaurant.class, "id", id));
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public void delete(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    public void deleteById(UUID id) {
        restaurantRepository.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return restaurantRepository.existsById(id);
    }
}
