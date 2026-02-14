package org.geeksforgeeks.food_ordering_app.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.MenuItem;
import org.geeksforgeeks.food_ordering_app.repository.jpa.MenuItemJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemJpaRepository menuItemJpaRepository;

    @Transactional
    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemJpaRepository.save(menuItem);
    }

    public Optional<MenuItem> getMenuItemById(UUID id) {
        return menuItemJpaRepository.findById(id);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemJpaRepository.findAll();
    }

    public List<MenuItem> getMenuItemsByRestaurantId(UUID restaurantId) {
        return menuItemJpaRepository.findByRestaurantId(restaurantId);
    }

    @Transactional
    public MenuItem updateMenuItem(UUID id, MenuItem menuItemDetails) {
        MenuItem menuItem = menuItemJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + id));
        
        menuItem.setName(menuItemDetails.getName());
        menuItem.setPrice(menuItemDetails.getPrice());
        menuItem.setDescription(menuItemDetails.getDescription());
        
        return menuItemJpaRepository.save(menuItem);
    }

    @Transactional
    public void deleteMenuItem(UUID id) {
        MenuItem menuItem = menuItemJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + id));
        menuItemJpaRepository.delete(menuItem);
    }
}
