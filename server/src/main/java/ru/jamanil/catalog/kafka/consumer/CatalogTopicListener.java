package ru.jamanil.catalog.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.jamanil.catalog.model.Item;
import ru.jamanil.catalog.service.ItemService;

/**
 * @author Victor Datsenko
 * 21.12.2022
 */
@Component
@RequiredArgsConstructor
public class CatalogTopicListener {
    private final ItemService service;
    @KafkaListener(id = "catalogListener", topics = "catalogTopic", containerFactory = "itemKafkaListenerContainerFactory")
    private void listen(Item item)  {
        System.out.println(item);
        service.save(item);
    }
}
