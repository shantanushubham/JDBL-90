package org.geeksforgeeks.food_ordering_app.controllers;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.Restaurant;
import org.geeksforgeeks.food_ordering_app.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody Restaurant restaurant) {
        try {
            Restaurant createdRestaurant = this.restaurantService.createRestaurant(restaurant);
            return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable UUID id) {
        try {
            return this.restaurantService.getRestaurantById(id)
                    .map(restaurant -> new ResponseEntity<>(restaurant, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRestaurants() {
        try {
            List<Restaurant> restaurants = this.restaurantService.getAllRestaurants();
            return new ResponseEntity<>(restaurants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(@PathVariable UUID id, @RequestBody Restaurant restaurantDetails) {
        try {
            Restaurant updatedRestaurant = this.restaurantService.updateRestaurant(id, restaurantDetails);
            return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable UUID id) {
        try {
            this.restaurantService.deleteRestaurant(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
