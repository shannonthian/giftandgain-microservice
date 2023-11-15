package com.giftandgain.inventorymanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.giftandgain.inventorymanagement.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgain.inventorymanagement.controller.CategoryController;
import com.giftandgain.inventorymanagement.entity.Category;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryRepository categoryRepository;

	// Testing for get all the category lists
	@Test
	public void testGetCategoryList() throws Exception {
		Category category1 = new Category(1L, "Books", "Unit", "A");
		Category category2 = new Category(2L, "Electronics", "Unit", "A");
		List<Category> categoryList = Arrays.asList(category1, category2);
		PageImpl<Category> page = new PageImpl<>(categoryList, PageRequest.of(0, 10), categoryList.size());
		when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(page);

		mockMvc.perform(get("/giftandgain/category?page=0&size=10")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(categoryList.size()));

		verify(categoryRepository, times(1)).findAll(any(PageRequest.class));
	}

	// Testing for get the selected item
	@Test
	public void testRetrieveCategory() throws Exception {
		Long categoryId = 1L;
		Category category = new Category(categoryId, "Books", "Unit", "A");
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

		mockMvc.perform(get("/giftandgain/category/" + categoryId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.categoryId").value(categoryId)).andExpect(jsonPath("$.category").value("Books"));

		verify(categoryRepository, times(1)).findById(categoryId);
	}

	// Testing for create category
	@Test
	public void testCreateCategory() throws Exception {
		Category category = new Category(null, "Books", "Unit", "A");
		Category savedCategory = new Category(1L, "Books", "Unit", "A");

		when(categoryRepository.existsByCategoryAndUnit(category.getCategory(), category.getUnit())).thenReturn(false);
		when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

		mockMvc.perform(post("/giftandgain/category/create").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(category))).andExpect(status().isCreated());

		verify(categoryRepository, times(1)).existsByCategoryAndUnit(category.getCategory(), category.getUnit());
		verify(categoryRepository, times(1)).save(any(Category.class));
	}

	// Testing for update category
	@Test
	public void testUpdateCategory() throws Exception {
		Long categoryId = 1L;
		Category originalCategory = new Category(categoryId, "Books", "Unit", "A");
		Category updatedCategory = new Category(categoryId, "Updated Books", "Unit", "A");

		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(originalCategory));
		when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

		mockMvc.perform(put("/giftandgain/category/edit/" + categoryId).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(updatedCategory))).andExpect(status().isOk())
				.andExpect(jsonPath("$.category").value("Updated Books"));

		verify(categoryRepository, times(1)).findById(categoryId);
		verify(categoryRepository, times(1)).save(any(Category.class));
	}

	// Testing for delete category
	@Test
	public void testDeleteCategory() throws Exception {
		Long categoryId = 1L;

		doNothing().when(categoryRepository).deleteById(categoryId);

		mockMvc.perform(delete("/giftandgain/category/delete/" + categoryId)).andExpect(status().isNoContent());

		verify(categoryRepository, times(1)).deleteById(categoryId);
	}

}
