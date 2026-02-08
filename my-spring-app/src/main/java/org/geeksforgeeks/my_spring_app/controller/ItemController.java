package org.geeksforgeeks.my_spring_app.controller;

import org.geeksforgeeks.my_spring_app.annotations.LogExecution;
import org.geeksforgeeks.my_spring_app.entities.Item;
import org.geeksforgeeks.my_spring_app.exceptions.NotFoundException;
import org.geeksforgeeks.my_spring_app.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/read")
    @LogExecution
    public List<Item> readItems() {
        return this.itemService.readItemsFromDB();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<?> readItemById(@PathVariable int id) {
        try {
            Item item = this.itemService.getItemById(id);
            return ResponseEntity.ok(item);
        } catch (NotFoundException e) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public Item addItem(@RequestBody Item item) {
        return this.itemService.addItem(item);
    }
}
