package com.giftandgain.inventorymanagement.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.giftandgain.inventorymanagement.entity.Category;
import com.giftandgain.inventorymanagement.repository.CategoryRepository;

@RestController
public class CategoryController {

	private CategoryRepository categoryRepo;

	public CategoryController(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}

	@GetMapping("/giftandgain/category")
	public ResponseEntity<List<Category>> getCategoryList(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String sort,
			@RequestParam(defaultValue = "asc") String direction) {

		Pageable pageable;

		// Check if sortBy parameter is provided
		if (sort != null && !sort.trim().isEmpty()) {
			if ("desc".equalsIgnoreCase(direction)) {
				pageable = PageRequest.of(page, size, Sort.by(sort).descending());
			} else {
				pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
			}
		} else {
			pageable = PageRequest.of(page, size); // No sorting
		}

		Page<Category> result = categoryRepo.findAll(pageable);
		long totalItems = result.getTotalElements(); // Get total number of items

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("x-total-count", String.valueOf(totalItems));

		return ResponseEntity.ok().headers(responseHeaders).body(result.getContent());
	}

	// Get selected target
	@GetMapping("giftandgain/category/{id}")
	public ResponseEntity<Category> retrieveCategory(@PathVariable Long id) {
		Optional<Category> category = categoryRepo.findById(id);

		if (category.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
		}
		return new ResponseEntity<>(category.get(), HttpStatus.OK);
	}

	// Create new target
	@PostMapping("giftandgain/category/create")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {

		// Check if category with the same name and unit already exists
		if (categoryRepo.existsByCategoryAndUnit(category.getCategory(), category.getUnit())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Category with the same name and unit already exists: " + category.getCategory() + ", "
							+ category.getUnit());
		}

		// Automatically set the status to 'A' for new categories
		category.setStatus("A");

		Category savedItem = categoryRepo.save(category);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedItem.getCategoryId()).toUri();

		return ResponseEntity.created(location).build();
	}

	// Update existing target
	@PutMapping("/giftandgain/category/edit/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory){

	    Optional<Category> existingCategory = categoryRepo.findById(id);
	    
	    if (existingCategory.isEmpty()) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
	    }

	    // Check for duplication of category name and unit with another record excluding the current one
	    if (categoryRepo.existsByCategoryAndUnitAndCategoryIdNot(updatedCategory.getCategory(), updatedCategory.getUnit(), id)) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Another category with the same name and unit exists: " + updatedCategory.getCategory() + ", " + updatedCategory.getUnit());
	    }

	 // Check if we're attempting to deactivate the only active category with that name
	    if ("D".equals(updatedCategory.getStatus()) && categoryRepo.countByCategoryAndStatusAndCategoryIdNot(updatedCategory.getCategory(), "A", id) == 0) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot deactivate the only active category with this name: " + updatedCategory.getCategory());
	    }

	    Category currentCategory = existingCategory.get();
	    currentCategory.setCategory(updatedCategory.getCategory());
	    currentCategory.setUnit(updatedCategory.getUnit());
	    currentCategory.setStatus(updatedCategory.getStatus());
	    
	    Category updatedCategories = categoryRepo.save(currentCategory);
	    
	    return ResponseEntity.ok(updatedCategories);
	}


	// Delete selected target
	@DeleteMapping("/giftandgain/category/delete/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		try {
			categoryRepo.deleteById(id);
			return ResponseEntity.noContent().build(); // HTTP 204 No Content
		} catch (EmptyResultDataAccessException e) {
			// If the item with the provided id does not exist, you might want to handle it
			// here
			return ResponseEntity.notFound().build(); // HTTP 404 Not Found
		}
	}

}
