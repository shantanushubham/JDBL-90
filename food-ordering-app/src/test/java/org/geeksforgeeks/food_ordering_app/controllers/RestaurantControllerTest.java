package org.geeksforgeeks.food_ordering_app.controllers;

import org.geeksforgeeks.food_ordering_app.entities.Restaurant;
import org.geeksforgeeks.food_ordering_app.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        restaurantController = new RestaurantController(restaurantService);
    }

    @Test
    void createRestaurant_returnsCreated() {
        Restaurant restaurant = new Restaurant();
        when(restaurantService.createRestaurant(any(Restaurant.class))).thenReturn(restaurant);

        ResponseEntity<?> result = restaurantController.createRestaurant(new Restaurant());

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void createRestaurant_returns500_onException() {
        when(restaurantService.createRestaurant(any(Restaurant.class))).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = restaurantController.createRestaurant(new Restaurant());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getRestaurantById_returnsOk_whenPresent() {
        UUID id = UUID.randomUUID();
        when(restaurantService.getRestaurantById(id)).thenReturn(Optional.of(new Restaurant()));

        ResponseEntity<?> result = restaurantController.getRestaurantById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getRestaurantById_returns404_whenEmpty() {
        UUID id = UUID.randomUUID();
        when(restaurantService.getRestaurantById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> result = restaurantController.getRestaurantById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getRestaurantById_returns500_onException() {
        UUID id = UUID.randomUUID();
        when(restaurantService.getRestaurantById(id)).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = restaurantController.getRestaurantById(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getAllRestaurants_returnsOk() {
        when(restaurantService.getAllRestaurants()).thenReturn(List.of());

        ResponseEntity<?> result = restaurantController.getAllRestaurants();

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAllRestaurants_returns500_onException() {
        when(restaurantService.getAllRestaurants()).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = restaurantController.getAllRestaurants();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void updateRestaurant_returnsOk() {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        when(restaurantService.updateRestaurant(eq(id), any(Restaurant.class))).thenReturn(restaurant);

        ResponseEntity<?> result = restaurantController.updateRestaurant(id, new Restaurant());

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void updateRestaurant_returns404_onRuntimeException() {
        UUID id = UUID.randomUUID();
        when(restaurantService.updateRestaurant(eq(id), any(Restaurant.class))).thenThrow(new RuntimeException("not found"));

        ResponseEntity<?> result = restaurantController.updateRestaurant(id, new Restaurant());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void updateRestaurant_returns404_whenServiceThrowsRuntimeException() {
        UUID id = UUID.randomUUID();
        when(restaurantService.updateRestaurant(eq(id), any(Restaurant.class))).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = restaurantController.updateRestaurant(id, new Restaurant());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteRestaurant_returnsNoContent() {
        UUID id = UUID.randomUUID();

        ResponseEntity<?> result = restaurantController.deleteRestaurant(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void deleteRestaurant_returns404_onRuntimeException() {
        UUID id = UUID.randomUUID();
        doThrow(new RuntimeException("not found")).when(restaurantService).deleteRestaurant(id);

        ResponseEntity<?> result = restaurantController.deleteRestaurant(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteRestaurant_returns404_whenServiceThrowsRuntimeException() {
        UUID id = UUID.randomUUID();
        doThrow(new RuntimeException("err")).when(restaurantService).deleteRestaurant(id);

        ResponseEntity<?> result = restaurantController.deleteRestaurant(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
