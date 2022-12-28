package ru.jamanil.catalog.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.jamanil.catalog.model.Item;

/**
 * @author Victor Datsenko
 * 21.12.2022
 */
@Component
@RequiredArgsConstructor
public class CatalogTopicProducer {

    private final KafkaTemplate<String, Item> kafkaTemplate;
    @Value("${spring.kafka.topic-name}")
    private String topicName;

    public void sendItemsToKafka(Item ... items) {
        for (Item item : items) {
            kafkaTemplate.send(topicName, item);
        }
    }
}
