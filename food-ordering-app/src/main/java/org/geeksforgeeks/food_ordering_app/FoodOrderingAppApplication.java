package org.geeksforgeeks.food_ordering_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FoodOrderingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodOrderingAppApplication.class, args);
    }

}
