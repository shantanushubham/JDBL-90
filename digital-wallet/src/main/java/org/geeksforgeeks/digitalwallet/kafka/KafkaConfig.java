package org.geeksforgeeks.digitalwallet.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    @Value("${app.kafka.reward-topic}")
    private String rewardTopic;

    @Bean
    public ProducerFactory<String, RewardEvent> rewardProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.springframework.kafka.support.serializer.JsonSerializer");
        // Suppress __TypeId__ headers so the consumer relies solely on the target type
        config.put("spring.json.add.type.headers", false);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, RewardEvent> rewardKafkaTemplate() {
        return new KafkaTemplate<>(this.rewardProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, RewardEvent> rewardConsumerFactory() {
        JsonDeserializer<RewardEvent> deserializer = new JsonDeserializer<>(RewardEvent.class);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeHeaders(false);

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, this.consumerGroupId);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RewardEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RewardEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(rewardConsumerFactory());
        return factory;
    }

    @Bean
    public NewTopic rewardTopic() {
        return TopicBuilder.name(this.rewardTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
