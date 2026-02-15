package org.geeksforgeeks.food_ordering_app.controllers;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.MenuItem;
import org.geeksforgeeks.food_ordering_app.service.MenuItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping("/{restaurantId}")
    public ResponseEntity<?> createMenuItem(
            @PathVariable UUID restaurantId,
            @RequestBody MenuItem menuItem) {
        try {
            MenuItem createdMenuItem = this.menuItemService.createMenuItem(
                    restaurantId, menuItem);
            return new ResponseEntity<>(createdMenuItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuItemById(@PathVariable UUID id) {
        try {
            return this.menuItemService.getMenuItemById(id)
                    .map(menuItem -> new ResponseEntity<>(menuItem, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllMenuItems() {
        try {
            List<MenuItem> menuItems = this.menuItemService.getAllMenuItems();
            return new ResponseEntity<>(menuItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> getMenuItemsByRestaurantId(@PathVariable UUID restaurantId) {
        try {
            List<MenuItem> menuItems = this.menuItemService.getMenuItemsByRestaurantId(restaurantId);
            return new ResponseEntity<>(menuItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenuItem(@PathVariable UUID id, @RequestBody MenuItem menuItemDetails) {
        try {
            MenuItem updatedMenuItem = this.menuItemService.updateMenuItem(id, menuItemDetails);
            return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable UUID id) {
        try {
            this.menuItemService.deleteMenuItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
