package org.geeksforgeeks.my_spring_app.playground;

import lombok.Builder;

@Builder
public class Engine {

    private String name;

    public void start() {
        System.out.println("Engine started");
    }

    public void init() {
        System.out.println("init of Engine was called.");
    }
}
