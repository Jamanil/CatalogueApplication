package ru.jamanil.catalog.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.jamanil.catalog.model.Item;
import ru.jamanil.catalog.model.Review;
import ru.jamanil.catalog.repository.ItemRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Victor Datsenko
 * 27.12.2022
 */
class ItemServiceTest {
    /*    @Mock
    private ItemService service;

    @Autowired
    private MockMvc mockMvc;

    private ItemRestController itemRestController;



    public ItemRestControllerTest() {
        MockitoAnnotations.openMocks(this);

        given(service.findAllItemContainsString("zero"))
                .willReturn(List.of());
        given(service.findAllItemContainsString("one"))
                .willReturn(List.of(new Item()));
        given(service.findAllItemContainsString("two"))
                .willReturn(List.of(new Item(), new Item()));
        given(service.findAllItemContainsString("three"))
                .willReturn(List.of(new Item(), new Item(), new Item()));

        this.itemRestController = new ItemRestController(service);
    }


    @Test
    public void findAllItemsContainsStringShouldReturnNull(){
        assertTrue(itemRestController.findAllItemsContainsString("zero").getBody().isEmpty());
    }

    @Test
    public void findAllItemsContainsStringShouldReturnTwoItems() {
        List<Item> itemListFromController = itemRestController.findAllItemsContainsString("two").getBody();
        assertEquals(2, itemListFromController.size());
    }

    @Test
    public void findAllItemContainsStringShouldThrowException() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () ->  itemRestController.findAllItemsContainsString(null));
        assertTrue(e.getMessage().contains("cant be null"));
        verify(service, never()).findAllItemContainsString(any());
    }

    @Test
    public void findItemsAndGetCountShouldReturnZero() {
        assertEquals(0, itemRestController.findItemsAndGetCount("zero").getBody());
    }

    @Test
    public void findItemsAndGetCountShouldReturnOneAndThree() {
        assertEquals(1, itemRestController.findItemsAndGetCount("one").getBody());
        assertEquals(3, itemRestController.findItemsAndGetCount("three").getBody());
    }

    @Test
    public void findItemsAndGetCountShouldSendResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getCount?findBy=three")).andExpectAll(
                status().is(HttpStatus.OK.value()),
                content().contentType("application/json"));
    }

    @Test
    public void handleExceptionShouldSendResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getCount"))
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(jsonPath(".title").value("FindBy argument error"))
                .andExpect(jsonPath(".detail").value("FindBy cant be null"));
    }*/
    private final ItemService service;
    @Mock
    private ItemRepository repository;

    public ItemServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.service = new ItemService(repository);
    }


    @Test
    public void findAllItemContainsString_whenNoMatches_shouldReturnEmptyList() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(Item.builder().name("1").build());
        itemList.add(Item.builder().name("22").build());
        itemList.add(Item.builder().name("3").build());
        itemList.add(Item.builder().name("4").build());
        Mockito.when(repository.findAll()).thenReturn(itemList);

        assertEquals(service.findAllItemContainsString("test"), List.of());
    }

    @Test
    public void findAllItemContainsString_shouldFindStringInAnyField() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(Item.builder().id("test").build());
        itemList.add(Item.builder().category("test").build());
        itemList.add(Item.builder().name("test").build());
        itemList.add(Item.builder().description("test").build());
        itemList.add(Item.builder().characteristics(Collections.singletonMap("test", "")).build());
        itemList.add(Item.builder().characteristics(Collections.singletonMap("", "test")).build());
        itemList.add(Item.builder().description("test").build());
        itemList.add(Item.builder().reviews(List.of(new Review("test", (short)0, new Date(), ""))).build());
        itemList.add(Item.builder().reviews(List.of(new Review("", (short)0, new Date(), "test"))).build());
        Mockito.when(repository.findAll()).thenReturn(itemList);

        assertEquals(itemList, service.findAllItemContainsString("test"));
    }

    @Test
    public void save_shouldUseSaveAllMethod() {
        service.save(new Item());
        Mockito.verify(repository, Mockito.times(1)).saveAll(Mockito.anyCollection());
    }


}