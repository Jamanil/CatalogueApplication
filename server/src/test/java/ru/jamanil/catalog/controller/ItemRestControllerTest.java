package ru.jamanil.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.jamanil.catalog.model.Item;
import ru.jamanil.catalog.service.ItemService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Victor Datsenko
 * 27.12.2022
 */

@SpringBootTest
@AutoConfigureMockMvc
class ItemRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private ItemService service;


    @Test
    public void findAllItemContainsString_whenNoMatches_ShouldResponseEmptyBody() throws Exception {
        Mockito.when(service.findAllItemContainsString(Mockito.anyString()))
                .thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/findBy?findBy=test"))
                .andExpectAll(
                        content().contentType("application/json"),
                        status().isOk(),
                        content().json(mapper.writeValueAsString(List.of()))
                );
    }

    @Test
    public void findAllItemContainsString_whenTwoMatches_ShouldResponseTwoItems() throws Exception {
        List<Item> itemList = new ArrayList<>();
        itemList.add(Item.builder().name("Item 1").build());
        itemList.add(Item.builder().name("Item 2").build());
        Mockito.when(service.findAllItemContainsString(Mockito.anyString()))
                .thenReturn(itemList);

        mockMvc.perform(MockMvcRequestBuilders.get("/findBy?findBy=test"))
                .andExpectAll(
                        content().contentType("application/json"),
                        status().isOk(),
                        content().json(mapper.writeValueAsString(itemList))
                );
    }

    @Test
    public void findAllItemContainsString_whenEmptyArg_ShouldResponseException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/findBy"))
                .andExpectAll(
                        content().contentType("application/json"),
                        status().isBadRequest(),
                        mvcResult -> mvcResult.getResolvedException().getClass().equals(IllegalArgumentException.class),
                        jsonPath(".title").value("FindBy argument error"),
                        jsonPath(".detail").value("FindBy cant be null"));
    }

    @Test
    public void findItemsAndGetCount_whenNoMatches_shouldReturn0() throws Exception {
        Mockito.when(service.findAllItemContainsString(Mockito.anyString()))
                .thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/getCount?findBy=test"))
                .andExpectAll(
                        content().contentType("application/json"),
                        status().isOk(),
                        content().json(mapper.writeValueAsString(0))
                );
    }

    @Test
    public void findItemsAndGetCount_when3Matches_shouldReturn3() throws Exception {
        List<Item> itemList = new ArrayList<>();
        itemList.add(Item.builder().name("Item 1").build());
        itemList.add(Item.builder().name("Item 2").build());
        itemList.add(Item.builder().name("Item 3").build());
        Mockito.when(service.findAllItemContainsString(Mockito.anyString()))
                .thenReturn(itemList);

        mockMvc.perform(MockMvcRequestBuilders.get("/getCount?findBy=test"))
                .andExpectAll(
                        content().contentType("application/json"),
                        status().isOk(),
                        content().json(mapper.writeValueAsString(3))
                );
    }
}