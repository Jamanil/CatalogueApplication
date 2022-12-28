package ru.jamanil.catalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * @author Victor Datsenko
 * 20.12.2022
 */
@Configuration
public class MongoDbConfig {
    @Autowired
    private void setMapKeyDotReplacement(MappingMongoConverter mongoConverter) {
        mongoConverter.setMapKeyDotReplacement("@DOT@");
    }
}
