package ru.jamanil.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.jamanil.catalog.kafka.producer.CatalogTopicProducer;
import ru.jamanil.catalog.model.Item;
import ru.jamanil.catalog.util.parser.CatalogParser;

import java.util.List;

/**
 * @author Victor Datsenko
 * 21.12.2022
 */
@Component
@RequiredArgsConstructor
public class ItemGuiController {
    private final CatalogParser parser;
    private final CatalogTopicProducer producer;


    public void parseCatalog(String catalogName) {
        List<Item> itemList = parser.parseCatalog(catalogName);
        producer.sendItemsToKafka(itemList.toArray(Item[]::new));
    }
}
