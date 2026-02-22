package org.geeksforgeeks.food_ordering_app.service;

import org.geeksforgeeks.food_ordering_app.entities.Address;
import org.geeksforgeeks.food_ordering_app.entities.Restaurant;
import org.geeksforgeeks.food_ordering_app.repository.jpa.RestaurantJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantJpaRepository restaurantJpaRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void createRestaurant_savesWithAddressBidirection() {
        Restaurant restaurant = new Restaurant();
        Address address = new Address();
        restaurant.setAddress(address);
        when(restaurantJpaRepository.save(restaurant)).thenReturn(restaurant);

        Restaurant result = restaurantService.createRestaurant(restaurant);

        assertSame(restaurant, result);
        verify(restaurantJpaRepository).save(restaurant);
    }

    @Test
    void createRestaurant_savesWhenAddressNull() {
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(null);
        when(restaurantJpaRepository.save(restaurant)).thenReturn(restaurant);

        Restaurant result = restaurantService.createRestaurant(restaurant);

        assertSame(restaurant, result);
    }

    @Test
    void getRestaurantById_returnsOptional() {
        UUID id = UUID.randomUUID();
        when(restaurantJpaRepository.findById(id)).thenReturn(Optional.empty());

        assertTrue(restaurantService.getRestaurantById(id).isEmpty());
    }

    @Test
    void getAllRestaurants_returnsList() {
        List<Restaurant> list = List.of(new Restaurant());
        when(restaurantJpaRepository.findAll()).thenReturn(list);

        assertSame(list, restaurantService.getAllRestaurants());
    }

    @Test
    void updateRestaurant_updatesAndSaves() {
        UUID id = UUID.randomUUID();
        Restaurant existing = new Restaurant();
        Restaurant details = new Restaurant();
        details.setName("New Name");
        when(restaurantJpaRepository.findById(id)).thenReturn(Optional.of(existing));
        when(restaurantJpaRepository.save(existing)).thenReturn(existing);

        Restaurant result = restaurantService.updateRestaurant(id, details);

        assertSame(existing, result);
        verify(restaurantJpaRepository).save(existing);
    }

    @Test
    void updateRestaurant_throwsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(restaurantJpaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> restaurantService.updateRestaurant(id, new Restaurant()));
    }

    @Test
    void deleteRestaurant_findsAndDeletes() {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        when(restaurantJpaRepository.findById(id)).thenReturn(Optional.of(restaurant));

        restaurantService.deleteRestaurant(id);

        verify(restaurantJpaRepository).delete(restaurant);
    }

    @Test
    void deleteRestaurant_throwsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(restaurantJpaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> restaurantService.deleteRestaurant(id));
    }
}
