package org.geeksforgeeks.digitalwallet.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RewardProducer {

    private final KafkaTemplate<String, RewardEvent> kafkaTemplate;

    @Value("${app.kafka.reward-topic}")
    private String rewardTopic;

    public void publishRewardEvent(RewardEvent event) {
        this.kafkaTemplate.send(this.rewardTopic, event.getSenderId().toString(), event);
        log.info("Published reward event for sender={}, amount={}",
                event.getSenderId(), event.getRewardAmount());
    }
}
