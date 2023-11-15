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
import com.giftandgain.inventorymanagement.entity.TargetInventory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgain.inventorymanagement.controller.TargetInventoryController;
import com.giftandgain.inventorymanagement.repository.TargetInventoryRepository;
import com.giftandgain.inventorymanagement.entity.Category;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(TargetInventoryController.class)
public class TargetInventoryControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TargetInventoryRepository targetRepository;

	// Sample Category Data
	Category grainsCategory = new Category(101L, "Grains", "kg", "A");
	Category beveragesCategory = new Category(102L, "Beverages", "ml", "A");
	Category cannedFoodCategory = new Category(103L, "Canned food", "counts", "A");
	Category frozenFoodCategory = new Category(104L, "Frozen Food", "packs", "A");

	// Sample TargetInventory Data
	TargetInventory target1 = new TargetInventory(101L, grainsCategory, new BigDecimal("100000.00"),
			LocalDate.parse("2023-09-10", DateTimeFormatter.ISO_LOCAL_DATE));
	TargetInventory target2 = new TargetInventory(102L, cannedFoodCategory, new BigDecimal("250.00"),
			LocalDate.parse("2023-09-10", DateTimeFormatter.ISO_LOCAL_DATE));
	// ... Continue for other target inventories as needed

	@Test
	public void testGetTargetList() throws Exception {
		List<TargetInventory> targetList = Arrays.asList(target1, target2);
		PageImpl<TargetInventory> page = new PageImpl<>(targetList, PageRequest.of(0, 10), targetList.size());
		when(targetRepository.findAll(any(Pageable.class))).thenReturn(page);

		mockMvc.perform(get("/giftandgain/target?page=0&size=10")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(targetList.size()));

		verify(targetRepository, times(1)).findAll(any(Pageable.class));
	}

	@Test
	public void testRetrieveTarget() throws Exception {
		Long targetId = 101L;
		when(targetRepository.findById(targetId)).thenReturn(Optional.of(target1));

		mockMvc.perform(get("/giftandgain/target/" + targetId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.targetId").value(targetId));

		verify(targetRepository, times(1)).findById(targetId);
	}

	@Test
	public void testCreateTarget() throws Exception {
		TargetInventory newTarget = new TargetInventory(null, grainsCategory, new BigDecimal("200000.00"),
				LocalDate.parse("2023-09-10", DateTimeFormatter.ISO_LOCAL_DATE));
		TargetInventory savedTarget = new TargetInventory(104L, grainsCategory, new BigDecimal("200000.00"),
				LocalDate.parse("2023-09-10", DateTimeFormatter.ISO_LOCAL_DATE));

		when(targetRepository.save(any(TargetInventory.class))).thenReturn(savedTarget);

		mockMvc.perform(post("/giftandgain/target/create").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(newTarget))).andExpect(status().isCreated());

		verify(targetRepository, times(1)).save(any(TargetInventory.class));
	}

	@Test
	public void testUpdateTarget() throws Exception {
		Long targetId = 101L;
		TargetInventory updatedTarget = new TargetInventory(targetId, grainsCategory, new BigDecimal("110000.00"),
				LocalDate.parse("2023-09-10", DateTimeFormatter.ISO_LOCAL_DATE));

		when(targetRepository.findById(targetId)).thenReturn(Optional.of(target1));
		when(targetRepository.save(any(TargetInventory.class))).thenReturn(updatedTarget);

		mockMvc.perform(put("/giftandgain/target/edit/" + targetId).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(updatedTarget))).andExpect(status().isOk());

		verify(targetRepository, times(1)).findById(targetId);
		verify(targetRepository, times(1)).save(any(TargetInventory.class));
	}

	@Test
	public void testDeleteTarget() throws Exception {
		Long targetId = 101L;
		doNothing().when(targetRepository).deleteById(targetId);

		mockMvc.perform(delete("/giftandgain/target/delete/" + targetId)).andExpect(status().isNoContent());

		verify(targetRepository, times(1)).deleteById(targetId);
	}

}
