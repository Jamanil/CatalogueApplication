package ru.jamanil.catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.jamanil.catalog.model.Item;

/**
 * @author Victor Datsenko
 * 16.12.2022
 */
@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
}
