package ru.jamanil.catalog.util.parser;

import org.springframework.beans.factory.annotation.Value;
import ru.jamanil.catalog.model.Item;

import java.util.List;

/**
 * @author Victor Datsenko
 * 19.12.2022
 */
public abstract class CatalogParser {
    @Value("${parser.user.agent}")
    protected String USER_AGENT;
    @Value("${parser.referrer}")
    protected String REFERRER;
    @Value("${parser.timeout}")
    protected int TIMEOUT;
    public abstract List<Item> parseCatalog(String catalogName);
}
