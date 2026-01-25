package org.geeksforgeeks.my_spring_app.playground;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestClass {

    private final Car car;

    public TestClass(Car car) {
        this.car = car;
    }

    @GetMapping("/test")
    public int test() {
        return this.car.start();
    }

}

@Component
class Car implements InitializingBean {

    private final Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet of Car is called.");
    }

    public int start() {
        this.engine.start();
        System.out.println("Car started.");
        return 1;
    }
}
