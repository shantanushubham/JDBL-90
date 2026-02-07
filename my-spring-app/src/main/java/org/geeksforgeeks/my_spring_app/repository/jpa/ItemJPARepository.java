package org.geeksforgeeks.my_spring_app.repository.jpa;

import org.geeksforgeeks.my_spring_app.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    // SELECT * FROM items WHERE price <= 1000 AND price >= 500;

    @Query("select i from Item i where i.price between ?1 and ?2")
    List<Item> getCheapItems(double priceStart, double priceEnd);
}
