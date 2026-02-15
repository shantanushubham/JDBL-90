package org.geeksforgeeks.food_ordering_app.service;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.MenuItem;
import org.geeksforgeeks.food_ordering_app.entities.Restaurant;
import org.geeksforgeeks.food_ordering_app.repository.MenuItemRepository;
import org.geeksforgeeks.food_ordering_app.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public MenuItem createMenuItem(UUID restaurantId, MenuItem menuItem) {
        Restaurant restaurant = this.restaurantRepository.findById(restaurantId);
        menuItem.setRestaurant(restaurant);
        return this.menuItemRepository.save(menuItem);
    }

    @Transactional
    public List<MenuItem> createMenuItems(UUID restaurantId, List<MenuItem> menuItems) {
        Restaurant restaurant = this.restaurantRepository.findById(restaurantId);
        menuItems.forEach(menuItem -> menuItem.setRestaurant(restaurant));
        return this.menuItemRepository.saveAll(menuItems);
    }

    public Optional<MenuItem> getMenuItemById(UUID id) {
        return menuItemRepository.findById(id);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> getMenuItemsByRestaurantId(UUID restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    @Transactional
    public MenuItem updateMenuItem(UUID id, MenuItem menuItemDetails) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + id));
        
        menuItem.setName(menuItemDetails.getName());
        menuItem.setPrice(menuItemDetails.getPrice());
        menuItem.setDescription(menuItemDetails.getDescription());
        
        return menuItemRepository.save(menuItem);
    }

    @Transactional
    public void deleteMenuItem(UUID id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + id));
        menuItemRepository.delete(menuItem);
    }
}
