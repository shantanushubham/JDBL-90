package com.example.addition_service.producers;

import com.example.addition_service.events.TestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestEventProducer {

    private final KafkaTemplate<String, TestEvent> kafkaTemplate;

    public void sendTestEvent(TestEvent testEvent) {
        this.kafkaTemplate.send("myTopic",
                        testEvent.getId().toString(),
                        testEvent)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send the event: {}", ex.getMessage());
                    } else {
                        log.info("TestEvent was sent: {} to partition: {}",
                                testEvent.getId(), result.getRecordMetadata().partition());
                    }
                });
    }
}
