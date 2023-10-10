package com.giftandgain.inventorymanagement.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.giftandgain.inventorymanagement.entity.Category;
import com.giftandgain.inventorymanagement.entity.InventoryManagement;
import com.giftandgain.inventorymanagement.repository.InventoryManagementRepository;
import com.giftandgain.inventorymanagement.specification.InventoryManagementSpecification;

@RestController
public class InventoryManagementController {

	private InventoryManagementRepository inventoryRepo;

	public InventoryManagementController(InventoryManagementRepository inventoryRepo) {
		this.inventoryRepo = inventoryRepo;
	}

	// Get all the items with pagination and sorting
	@GetMapping("/giftandgain/inventory")
	public ResponseEntity<List<InventoryManagement>> getInventoryList(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String sort,
			@RequestParam(defaultValue = "asc") String direction) {

		Pageable pageable;
		 if ("category".equalsIgnoreCase(sort)) {
		        sort = "category.category";
		    }

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

		Page<InventoryManagement> result = inventoryRepo.findAll(pageable);
		long totalItems = result.getTotalElements(); // Get total number of items

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("x-total-count", String.valueOf(totalItems));

		return ResponseEntity.ok().headers(responseHeaders).body(result.getContent());
	}

	// Get selected item
	@GetMapping("/giftandgain/inventory/{id}")
	public ResponseEntity<InventoryManagement> retrieveItem(@PathVariable Long id) {
		Optional<InventoryManagement> inventoryManagement = inventoryRepo.findById(id);

		if (!inventoryManagement.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
		}

		return new ResponseEntity<>(inventoryManagement.get(), HttpStatus.OK);
	}

	// Searching within the expiry date
	@GetMapping("/giftandgain/inventory/search")
	public ResponseEntity<List<InventoryManagement>> searchInventory(@RequestParam(required = false) String itemName,
			@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) BigDecimal receivedQuantity,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String expiryStartDateStr,
			@RequestParam(required = false) String expiryEndDateStr,
			@RequestParam(required = false) String createdStartDateStr,
		    @RequestParam(required = false) String createdEndDateStr,
		    @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String sort,
			@RequestParam(defaultValue = "asc") String direction) {

		LocalDate expiryStartDate = expiryStartDateStr != null
				? LocalDate.parse(expiryStartDateStr, DateTimeFormatter.ofPattern("yyyyMMdd"))
				: null;
		LocalDate expiryEndDate = expiryEndDateStr != null
				? LocalDate.parse(expiryEndDateStr, DateTimeFormatter.ofPattern("yyyyMMdd"))
				: null;
		LocalDate createdStartDate = createdStartDateStr != null
	            ? LocalDate.parse(createdStartDateStr, DateTimeFormatter.ofPattern("yyyyMMdd"))
	            : null;
	    LocalDate createdEndDate = createdEndDateStr != null
	            ? LocalDate.parse(createdEndDateStr, DateTimeFormatter.ofPattern("yyyyMMdd"))
	            : null;

	    Specification<InventoryManagement> spec = Specification
	    	    .where(InventoryManagementSpecification.hasCategory(categoryId))
				.and(InventoryManagementSpecification.hasItemName(itemName))
				.and(InventoryManagementSpecification.hasReceivedQuantity(receivedQuantity))
				.and(InventoryManagementSpecification.hasCreatedBy(createdBy))
				.and(InventoryManagementSpecification.isExpiryDateBetween(expiryStartDate, expiryEndDate))
				.and(InventoryManagementSpecification.isCreatedDateBetween(createdStartDate, createdEndDate));

		Pageable pageable;

		if (sort != null && !sort.trim().isEmpty()) {
			if ("desc".equalsIgnoreCase(direction)) {
				pageable = PageRequest.of(page, size, Sort.by(sort).descending());
			} else {
				pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
			}
		} else {
			pageable = PageRequest.of(page, size); // No sorting
		}

		Page<InventoryManagement> result = inventoryRepo.findAll(spec, pageable);
		long totalItems = result.getTotalElements();

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("x-total-count", String.valueOf(totalItems));

		return ResponseEntity.ok().headers(responseHeaders).body(result.getContent());
	}

	// Create new item
	@PostMapping("/giftandgain/inventory/create")
	public ResponseEntity<InventoryManagement> createItem(@RequestBody InventoryManagement inventoryManagement) {
		// Set the createdDate to the current date and time
		inventoryManagement.setCreatedDateToNow();

		// Save the record with the current date as the createdDate
		InventoryManagement savedItem = inventoryRepo.save(inventoryManagement);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedItem.getInventoryId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/giftandgain/inventory/edit/{id}")
	public ResponseEntity<InventoryManagement> editItem(@PathVariable Long id,
			@RequestBody InventoryManagement updatedInventory) {
		Optional<InventoryManagement> inventoryOpt = inventoryRepo.findById(id);

		if (!inventoryOpt.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
		}

		InventoryManagement currentInventory = inventoryOpt.get();
		currentInventory.setItemName(updatedInventory.getItemName());
		currentInventory.setCategory(updatedInventory.getCategory());
		currentInventory.setReceivedQuantity(updatedInventory.getReceivedQuantity());
		currentInventory.setExpiryDate(updatedInventory.getExpiryDate());
		currentInventory.setCreatedDateToNow();
		inventoryRepo.save(currentInventory);

		return new ResponseEntity<>(currentInventory, HttpStatus.OK);
	}

	// Delete selected item
	@DeleteMapping("/giftandgain/inventory/delete/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
		try {
			inventoryRepo.deleteById(id);
			return ResponseEntity.noContent().build(); // HTTP 204 No Content
		} catch (EmptyResultDataAccessException e) {
			// If the item with the provided id does not exist, you might want to handle it
			// here
			return ResponseEntity.notFound().build(); // HTTP 404 Not Found
		}
	}

	// Get high priority category
	@GetMapping("giftandgain/inventory/highpriority/{month}/{year}")
	public ResponseEntity<List<String>> getHighPriorityCategories(@PathVariable int month, @PathVariable int year) {
		List<String> highPriorityCategories = inventoryRepo.getHighPriorityList(month, year);
		return new ResponseEntity<>(highPriorityCategories, HttpStatus.OK);
	}

	// Get low priority category
	@GetMapping("giftandgain/inventory/lowpriority/{month}/{year}")
	public ResponseEntity<List<String>> getLowPriorityCategories(@PathVariable int month, @PathVariable int year) {
		List<String> lowPriorityCategories = inventoryRepo.getLowPriorityList(month, year);
		return new ResponseEntity<>(lowPriorityCategories, HttpStatus.OK);
	}

}
