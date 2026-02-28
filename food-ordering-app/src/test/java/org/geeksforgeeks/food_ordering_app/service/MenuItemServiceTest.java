package org.geeksforgeeks.food_ordering_app.service;

import org.geeksforgeeks.food_ordering_app.entities.MenuItem;
import org.geeksforgeeks.food_ordering_app.entities.Restaurant;
import org.geeksforgeeks.food_ordering_app.repository.MenuItemRepository;
import org.geeksforgeeks.food_ordering_app.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private MenuItemService menuItemService;

    @Test
    void createMenuItem_setsRestaurantAndSaves() {
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        MenuItem menuItem = new MenuItem();
        when(restaurantRepository.findById(restaurantId)).thenReturn(restaurant);
        when(menuItemRepository.save(menuItem)).thenReturn(menuItem);

        MenuItem result = menuItemService.createMenuItem(restaurantId, menuItem);

        assertSame(menuItem, result);
        verify(menuItemRepository).save(menuItem);
    }

    @Test
    void createMenuItems_setsRestaurantAndSavesAll() {
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        MenuItem mi = new MenuItem();
        List<MenuItem> items = List.of(mi);
        when(restaurantRepository.findById(restaurantId)).thenReturn(restaurant);
        when(menuItemRepository.saveAll(items)).thenReturn(items);

        List<MenuItem> result = menuItemService.createMenuItems(restaurantId, items);

        assertSame(items, result);
        verify(menuItemRepository).saveAll(items);
    }

    @Test
    void getMenuItemById_returnsOptional() {
        UUID id = UUID.randomUUID();
        when(menuItemRepository.findById(id)).thenReturn(Optional.empty());

        assertTrue(menuItemService.getMenuItemById(id).isEmpty());
    }

    @Test
    void getAllMenuItems_returnsList() {
        List<MenuItem> list = List.of(new MenuItem());
        when(menuItemRepository.findAll()).thenReturn(list);

        assertSame(list, menuItemService.getAllMenuItems());
    }

    @Test
    void getMenuItemsByRestaurantId_returnsList() {
        UUID restaurantId = UUID.randomUUID();
        List<MenuItem> list = List.of(new MenuItem());
        when(menuItemRepository.findByRestaurantId(restaurantId)).thenReturn(list);

        assertSame(list, menuItemService.getMenuItemsByRestaurantId(restaurantId));
    }

    @Test
    void updateMenuItem_updatesAndSaves() {
        UUID id = UUID.randomUUID();
        MenuItem existing = new MenuItem();
        MenuItem details = new MenuItem();
        details.setName("n");
        details.setPrice(10.0);
        details.setDescription("d");
        when(menuItemRepository.findById(id)).thenReturn(Optional.of(existing));
        when(menuItemRepository.save(existing)).thenReturn(existing);

        MenuItem result = menuItemService.updateMenuItem(id, details);

        assertSame(existing, result);
        verify(menuItemRepository).save(existing);
    }

    @Test
    void updateMenuItem_throwsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(menuItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> menuItemService.updateMenuItem(id, new MenuItem()));
    }

    @Test
    void deleteMenuItem_findsAndDeletes() {
        UUID id = UUID.randomUUID();
        MenuItem menuItem = new MenuItem();
        when(menuItemRepository.findById(id)).thenReturn(Optional.of(menuItem));

        menuItemService.deleteMenuItem(id);

        verify(menuItemRepository).delete(menuItem);
    }

    @Test
    void deleteMenuItem_throwsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(menuItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> menuItemService.deleteMenuItem(id));
    }
}
