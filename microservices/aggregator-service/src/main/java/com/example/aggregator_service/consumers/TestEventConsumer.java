package com.example.aggregator_service.consumers;

import com.example.aggregator_service.events.TestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestEventConsumer {

    @KafkaListener(topics = "myTopic", groupId = "aggregate-service")
    public void consumeTestEvent(TestEvent event) {
        log.info("Received Test Event: {}", event.getId());
        System.out.println(event.getName());
    }
}
