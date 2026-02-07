package org.geeksforgeeks.my_spring_app.controller;

import org.aspectj.weaver.ast.Not;
import org.geeksforgeeks.my_spring_app.annotations.LogExecution;
import org.geeksforgeeks.my_spring_app.exceptions.NotFoundException;
import org.geeksforgeeks.my_spring_app.jdbc.MyJDBC;
import org.geeksforgeeks.my_spring_app.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

@RestController
public class ItemController {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final File file = new File("src/item.json");

    private final MyJDBC myJDBC;

    @Autowired
    public ItemController(MyJDBC myJDBC) {
        this.myJDBC = myJDBC;
    }

    @GetMapping("/read")
    @LogExecution
    public List<Item> readItems() {
//        return objectMapper.readValue(file,
//                new TypeReference<List<Item>>() {
//                });
        return this.myJDBC.readItemsFromDB();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<?> readItemById(@PathVariable int id) {
        try {
            Item item = this.myJDBC.getItemById(id);
            return ResponseEntity.ok(item);
        } catch (NotFoundException e) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/add")
    public Item addItem(@RequestBody Item item) {
        return this.myJDBC.addItem(item);
    }
}
