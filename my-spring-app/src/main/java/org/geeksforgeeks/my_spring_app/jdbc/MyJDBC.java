package org.geeksforgeeks.my_spring_app.jdbc;

import org.geeksforgeeks.my_spring_app.entities.Item;
import org.geeksforgeeks.my_spring_app.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class MyJDBC {

    private final ItemRepository itemRepository;

    @Autowired
    public MyJDBC(ItemRepository itemRepository) {
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

// ddl - data definition language // CREATE, ALTER, DROP, TRUNCATE, RENAME
// dml - data manipulation language // INSERT, SELECT, UPDATE, DELETE
