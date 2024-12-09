package jpabook.jpashop.service;

import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @DisplayName("신규아이템저장성공 ")
    @Test
    void saveItem_NewItem_Success() {
        // Given
        Item newItem = new Book();
        newItem.setName("New Item");
        newItem.setPrice(1000);
        newItem.setStockQuantity(10);

        when(itemRepository.save(newItem)).thenReturn(newItem);

        // When
        itemService.saveItem(newItem);

        // Then
        verify(itemRepository, times(1)).save(newItem);
    }

    @Test
    void saveItem_UpdateExistingItem_Success() {
        // Given
        Item existingItem = new Book();
        existingItem.setId(1L);
        existingItem.setName("Existing Item");
        existingItem.setPrice(1000);
        existingItem.setStockQuantity(10);

        when(itemRepository.findById(existingItem.getId())).thenReturn(Optional.of(existingItem));

        // When
        itemService.saveItem(existingItem);

        // Then
        verify(itemRepository, times(1)).findById(existingItem.getId());
    }

    @Test
    void findItems_ReturnsAllItems() {
        // Given
        Item item1 = new Book();
        item1.setName("Item 1");
        item1.setPrice(500);

        Item item2 = new Book();
        item2.setName("Item 2");
        item2.setPrice(1500);

        List<Item> items = Arrays.asList(item1, item2);
        when(itemRepository.findAll()).thenReturn(items);

        // When
        List<Item> result = itemService.findItems();

        // Then
        assertEquals(2, result.size());
        assertEquals("Item 1", result.get(0).getName());
        assertEquals("Item 2", result.get(1).getName());
    }

    @Test
    void findOne_ItemExists_ReturnsItem() {
        // Given
        Long itemId = 1L;
        Item item = new Book();
        item.setId(itemId);
        item.setName("Test Item");

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        // When
        Item result = itemService.findOne(itemId);

        // Then
        assertNotNull(result);
        assertEquals("Test Item", result.getName());
    }

    @Test
    void findOne_ItemDoesNotExist_ThrowsException() {
        // Given
        Long itemId = 999L;

        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            itemService.findOne(itemId);
        });

        assertEquals("해당 아이템이 존재하지 않습니다. id=999", exception.getMessage());
    }
}
