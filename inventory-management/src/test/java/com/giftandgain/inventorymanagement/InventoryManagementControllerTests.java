package com.giftandgain.inventorymanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.giftandgain.inventorymanagement.entity.InventoryManagement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgain.inventorymanagement.controller.InventoryManagementController;
import com.giftandgain.inventorymanagement.repository.InventoryManagementRepository;
import com.giftandgain.inventorymanagement.entity.Category;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(InventoryManagementController.class)
public class InventoryManagementControllerTests {
	
	Category category1 = new Category(101L, "Grains", "kg", "A");
	Category category2 = new Category(102L, "Beverages", "ml", "A");


	InventoryManagement item1 = new InventoryManagement(10001L, "Rice", category1, new BigDecimal("200"), LocalDate.parse("2025-09-10"), LocalDate.parse("2023-09-10"), "This is a dry food", "admin123");
	InventoryManagement item2 = new InventoryManagement(10002L, "Noodles", category1, new BigDecimal("30.5"), LocalDate.parse("2025-09-11"), LocalDate.parse("2023-09-11"), "This is a dry food", "admin123");



    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryManagementRepository inventoryRepository;

    @Test
    public void testGetInventoryList() throws Exception {
        List<InventoryManagement> itemList = Arrays.asList(item1, item2);
        PageImpl<InventoryManagement> page = new PageImpl<>(itemList, PageRequest.of(0, 10), itemList.size());
        when(inventoryRepository.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/giftandgain/inventory?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(itemList.size()));

        verify(inventoryRepository, times(1)).findAll(any(Pageable.class));
    }
    
    @Test
    public void testRetrieveItem() throws Exception {
        Long itemId = 10001L;
        when(inventoryRepository.findById(itemId)).thenReturn(Optional.of(item1));

        mockMvc.perform(get("/giftandgain/inventory/" + itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").value(itemId));

        verify(inventoryRepository, times(1)).findById(itemId);
    }
    
    @Test
    public void testCreateItem() throws Exception {
        InventoryManagement newItem = new InventoryManagement(null, "New Item", category2, new BigDecimal("100"), LocalDate.now(), LocalDate.now(), "New item description", "user123");
        InventoryManagement savedItem = new InventoryManagement(10008L, "New Item", category2, new BigDecimal("100"), LocalDate.now(), LocalDate.now(), "New item description", "user123");

        when(inventoryRepository.save(any(InventoryManagement.class))).thenReturn(savedItem);

        mockMvc.perform(post("/giftandgain/inventory/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newItem)))
                .andExpect(status().isCreated());

        verify(inventoryRepository, times(1)).save(any(InventoryManagement.class));
    }

    @Test
    public void testEditItem() throws Exception {
        Long itemId = 10001L;
        InventoryManagement updatedItem = new InventoryManagement(itemId, "Updated Rice", category1, new BigDecimal("250"), LocalDate.parse("2025-09-15"), LocalDate.parse("2023-09-12"), "Updated description", "adminNew");

        when(inventoryRepository.findById(itemId)).thenReturn(Optional.of(item1));
        when(inventoryRepository.save(any(InventoryManagement.class))).thenReturn(updatedItem);

        mockMvc.perform(put("/giftandgain/inventory/edit/" + itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedItem)))
                .andExpect(status().isOk());

        verify(inventoryRepository, times(1)).findById(itemId);
        verify(inventoryRepository, times(1)).save(any(InventoryManagement.class));
    }

    @Test
    public void testDeleteItem() throws Exception {
        Long itemId = 10001L;
        doNothing().when(inventoryRepository).deleteById(itemId);

        mockMvc.perform(delete("/giftandgain/inventory/delete/" + itemId))
                .andExpect(status().isNoContent());

        verify(inventoryRepository, times(1)).deleteById(itemId);
    }


}

