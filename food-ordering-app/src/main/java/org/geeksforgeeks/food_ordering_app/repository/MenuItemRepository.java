package org.geeksforgeeks.food_ordering_app.repository;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.entities.MenuItem;
import org.geeksforgeeks.food_ordering_app.repository.jpa.MenuItemJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MenuItemRepository {

    private final MenuItemJpaRepository menuItemJpaRepository;

    public MenuItem save(MenuItem menuItem) {
        return menuItemJpaRepository.save(menuItem);
    }

    public List<MenuItem> saveAll(List<MenuItem> menuItemList) {
        return this.menuItemJpaRepository.saveAll(menuItemList);
    }

    public Optional<MenuItem> findById(UUID id) {
        return menuItemJpaRepository.findById(id);
    }

    public List<MenuItem> findAll() {
        return menuItemJpaRepository.findAll();
    }

    public List<MenuItem> findAllByIdList(Collection<UUID> uuids) {
        return this.menuItemJpaRepository.findAllById(uuids);
    }

    public List<MenuItem> findByRestaurantId(UUID restaurantId) {
        return menuItemJpaRepository.findByRestaurantId(restaurantId);
    }

    public void delete(MenuItem menuItem) {
        menuItemJpaRepository.delete(menuItem);
    }

    public void deleteById(UUID id) {
        menuItemJpaRepository.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return menuItemJpaRepository.existsById(id);
    }
}
