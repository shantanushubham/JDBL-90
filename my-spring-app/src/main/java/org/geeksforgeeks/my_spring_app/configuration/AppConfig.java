package org.geeksforgeeks.my_spring_app.configuration;

import org.geeksforgeeks.my_spring_app.playground.Engine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Engine myEngine() {
        return Engine.builder()
                .name("Tata Engine")
                .build();
    }

}
