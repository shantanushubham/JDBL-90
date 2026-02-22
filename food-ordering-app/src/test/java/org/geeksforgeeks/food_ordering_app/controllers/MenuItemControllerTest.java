package org.geeksforgeeks.food_ordering_app.controllers;

import org.geeksforgeeks.food_ordering_app.entities.MenuItem;
import org.geeksforgeeks.food_ordering_app.service.MenuItemService;
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
class MenuItemControllerTest {

    @Mock
    private MenuItemService menuItemService;

    private MenuItemController menuItemController;

    @BeforeEach
    void setUp() {
        menuItemController = new MenuItemController(menuItemService);
    }

    @Test
    void createMenuItem_returnsCreated() {
        UUID restaurantId = UUID.randomUUID();
        MenuItem menuItem = new MenuItem();
        when(menuItemService.createMenuItem(eq(restaurantId), any(MenuItem.class))).thenReturn(menuItem);

        ResponseEntity<?> result = menuItemController.createMenuItem(restaurantId, new MenuItem());

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void createMenuItem_returns500_onException() {
        UUID restaurantId = UUID.randomUUID();
        when(menuItemService.createMenuItem(eq(restaurantId), any(MenuItem.class))).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = menuItemController.createMenuItem(restaurantId, new MenuItem());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getMenuItemById_returnsOk_whenPresent() {
        UUID id = UUID.randomUUID();
        when(menuItemService.getMenuItemById(id)).thenReturn(Optional.of(new MenuItem()));

        ResponseEntity<?> result = menuItemController.getMenuItemById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getMenuItemById_returns404_whenEmpty() {
        UUID id = UUID.randomUUID();
        when(menuItemService.getMenuItemById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> result = menuItemController.getMenuItemById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getMenuItemById_returns500_onException() {
        UUID id = UUID.randomUUID();
        when(menuItemService.getMenuItemById(id)).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = menuItemController.getMenuItemById(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getAllMenuItems_returnsOk() {
        when(menuItemService.getAllMenuItems()).thenReturn(List.of());

        ResponseEntity<?> result = menuItemController.getAllMenuItems();

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAllMenuItems_returns500_onException() {
        when(menuItemService.getAllMenuItems()).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = menuItemController.getAllMenuItems();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getMenuItemsByRestaurantId_returnsOk() {
        UUID restaurantId = UUID.randomUUID();
        when(menuItemService.getMenuItemsByRestaurantId(restaurantId)).thenReturn(List.of());

        ResponseEntity<?> result = menuItemController.getMenuItemsByRestaurantId(restaurantId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getMenuItemsByRestaurantId_returns500_onException() {
        UUID restaurantId = UUID.randomUUID();
        when(menuItemService.getMenuItemsByRestaurantId(restaurantId)).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = menuItemController.getMenuItemsByRestaurantId(restaurantId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void updateMenuItem_returnsOk() {
        UUID id = UUID.randomUUID();
        MenuItem menuItem = new MenuItem();
        when(menuItemService.updateMenuItem(eq(id), any(MenuItem.class))).thenReturn(menuItem);

        ResponseEntity<?> result = menuItemController.updateMenuItem(id, new MenuItem());

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void updateMenuItem_returns404_onRuntimeException() {
        UUID id = UUID.randomUUID();
        when(menuItemService.updateMenuItem(eq(id), any(MenuItem.class))).thenThrow(new RuntimeException("not found"));

        ResponseEntity<?> result = menuItemController.updateMenuItem(id, new MenuItem());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void updateMenuItem_returns404_whenServiceThrowsRuntimeException() {
        UUID id = UUID.randomUUID();
        when(menuItemService.updateMenuItem(eq(id), any(MenuItem.class))).thenThrow(new RuntimeException("err"));

        ResponseEntity<?> result = menuItemController.updateMenuItem(id, new MenuItem());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteMenuItem_returnsNoContent() {
        UUID id = UUID.randomUUID();

        ResponseEntity<?> result = menuItemController.deleteMenuItem(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void deleteMenuItem_returns404_onRuntimeException() {
        UUID id = UUID.randomUUID();
        doThrow(new RuntimeException("not found")).when(menuItemService).deleteMenuItem(id);

        ResponseEntity<?> result = menuItemController.deleteMenuItem(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteMenuItem_returns404_whenServiceThrowsRuntimeException() {
        UUID id = UUID.randomUUID();
        doThrow(new RuntimeException("err")).when(menuItemService).deleteMenuItem(id);

        ResponseEntity<?> result = menuItemController.deleteMenuItem(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
