package org.geeksforgeeks.my_spring_app.controller;

import org.geeksforgeeks.my_spring_app.annotations.LogExecution;
import org.geeksforgeeks.my_spring_app.models.Item;
import org.springframework.web.bind.annotation.*;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

@RestController
public class ItemController {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final File file = new File("src/item.json");

    @GetMapping("/read")
    @LogExecution
    public List<Item> readItems() {
        return objectMapper.readValue(file,
                new TypeReference<List<Item>>() {
                });
    }

    @GetMapping("/read/{id}")
    public Item readItemById(@PathVariable int id) {
        List<Item> itemList = this.readItems().stream()
                .filter(el -> el.getId() == id)
                .toList();
        if (itemList.isEmpty()) {
            return null;
        }
        return itemList.get(0);
    }

    @PostMapping("/add")
    public Item addItem(@RequestBody Item item) {
        List<Item> itemList = this.readItems();
        boolean isDuplicateItemId = itemList.stream()
                .anyMatch(el -> el.getId() == item.getId());
        if (isDuplicateItemId) {
            return null;
        } else {
            itemList.add(item);
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, itemList);
            return item;
        }
    }
}
