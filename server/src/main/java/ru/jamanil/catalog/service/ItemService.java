package ru.jamanil.catalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jamanil.catalog.model.Item;
import ru.jamanil.catalog.repository.ItemRepository;

import java.util.Arrays;
import java.util.List;

/**
 * @author Victor Datsenko
 * 24.12.2022
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> findAllItemContainsString(String findBy) {
        List<Item> allItems = itemRepository.findAll();
        return allItems.stream().filter(item -> item.toString().contains(findBy)).toList();
    }

    public void save(Item ... items) {
        itemRepository.saveAll(Arrays.stream(items).toList());
    }
}
