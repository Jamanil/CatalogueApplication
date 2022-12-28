package ru.jamanil.catalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.jamanil.catalog.model.Item;

import java.util.Objects;


/**
 * @author Victor Datsenko
 * 24.12.2022
 */
@Component
@RequiredArgsConstructor
public class ClientItemService {
    @Value("${server.application.ip}")
    private String SERVER_IP;
    @Value("${server.application.port}")
    private String SERVER_PORT;
    private final RestTemplate restTemplate = new RestTemplate();

    public Item[] sendFindItemRequest(String findBy) {
        String url = String.format("http://%s:%s/findBy?findBy=%s", SERVER_IP, SERVER_PORT, findBy);
        return Objects.requireNonNull(restTemplate.getForObject(url, Item[].class));
    }
}