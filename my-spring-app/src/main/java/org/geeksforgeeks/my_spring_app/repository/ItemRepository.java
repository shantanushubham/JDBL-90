package org.geeksforgeeks.my_spring_app.repository;

import jakarta.persistence.EntityNotFoundException;
import org.geeksforgeeks.my_spring_app.entities.Item;
import org.geeksforgeeks.my_spring_app.exceptions.NotFoundException;
import org.geeksforgeeks.my_spring_app.repository.jpa.ItemJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class ItemRepository {

    private final ItemJPARepository itemJPARepository;

    @Autowired
    public ItemRepository(ItemJPARepository itemJPARepository) {
        this.itemJPARepository = itemJPARepository;
    }

    public Item getItemById(int id) {
        return this.itemJPARepository.findById(id).orElseThrow(() -> new NotFoundException(Item.class, "id", id));
    }

    public List<Item> getAllItems() {
        return this.itemJPARepository.findAll();
    }

    public Item saveItem(Item item) {
        return this.itemJPARepository.save(item);
    }

    public void deleteById(int id) {
        this.itemJPARepository.deleteById(id);
    }

    public List<Item> getItemsByIds(Collection<Integer> ids) {
        return this.itemJPARepository.findByIdIn(ids);
    }

    public List<Item> saveAll(List<Item> itemList) {
        return this.itemJPARepository.saveAll(itemList);
    }
}
