package org.geeksforgeeks.my_spring_app.service;

import org.geeksforgeeks.my_spring_app.entities.Item;
import org.geeksforgeeks.my_spring_app.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> readItemsFromDB() {
        return this.itemRepository.getAllItems();
    }

    public Item addItem(Item item) {
        return this.itemRepository.saveItem(item);
    }

    public Item getItemById(int id) {
        return this.itemRepository.getItemById(id);
    }
}

// Controller -> Service -> Repository -> DB