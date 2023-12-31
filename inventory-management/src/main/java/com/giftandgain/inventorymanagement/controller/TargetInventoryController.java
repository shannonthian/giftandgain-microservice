package com.giftandgain.inventorymanagement.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Base64;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
import com.giftandgain.inventorymanagement.entity.TargetInventory;
import com.giftandgain.inventorymanagement.repository.TargetInventoryRepository;
import com.giftandgain.inventorymanagement.specification.TargetInventorySpecification;

@RestController
public class TargetInventoryController {

	private TargetInventoryRepository targetRepo;

	public TargetInventoryController(TargetInventoryRepository targetRepo) {
		this.targetRepo = targetRepo;
	}

	@GetMapping("/giftandgain/target")
	public ResponseEntity<List<TargetInventory>> getTargetList(@RequestParam(defaultValue = "0") int page,
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

		Page<TargetInventory> result = targetRepo.findAll(pageable);
		long totalItems = result.getTotalElements(); // Get total number of items

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("x-total-count", String.valueOf(totalItems));

		return ResponseEntity.ok().headers(responseHeaders).body(result.getContent());
	}

	// Get selected category
	@GetMapping("/giftandgain/target/{id}")
	public ResponseEntity<TargetInventory> retrieveTarget(@PathVariable Long id) {
		Optional<TargetInventory> inventoryManagement = targetRepo.findById(id);

		if (!inventoryManagement.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
		}

		return new ResponseEntity<>(inventoryManagement.get(), HttpStatus.OK);
	}

	// Search
	@GetMapping("/giftandgain/target/search")
	public ResponseEntity<List<TargetInventory>> searchTarget(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, 
			@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) BigDecimal targetQuantity,
			@RequestParam(required = false) String targetMonthYearStr, @RequestParam(required = false) String sort,
			@RequestParam(defaultValue = "asc") String direction) {

		LocalDate targetMonthYear = targetMonthYearStr != null
				? LocalDate.parse(targetMonthYearStr, DateTimeFormatter.ofPattern("yyyyMMdd"))
				: null;

		Specification<TargetInventory> spec = Specification
				.where(TargetInventorySpecification.hasCategory(categoryId))
				.and(TargetInventorySpecification.hasTargetQuantity(targetQuantity))
				.and(TargetInventorySpecification.hasTargetMonthYear(targetMonthYear));

		Pageable pageable;

		if (sort != null && !sort.trim().isEmpty()) {
			if ("desc".equalsIgnoreCase(direction)) {
				pageable = PageRequest.of(page, size, Sort.by(sort).descending());
			} else {
				pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
			}
		} else {
			pageable = PageRequest.of(page, size);
		}

		Page<TargetInventory> result = targetRepo.findAll(spec, pageable);
		long totalItems = result.getTotalElements();

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("x-total-count", String.valueOf(totalItems));

		return ResponseEntity.ok().headers(responseHeaders).body(result.getContent());
	}

	// Create new category
	@PostMapping("/giftandgain/target/create")
	public ResponseEntity<TargetInventory> createTarget(@RequestBody TargetInventory targetInventory) {

		// Save the record with the current date as the createdDate
		TargetInventory savedItem = targetRepo.save(targetInventory);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedItem.getTargetId()).toUri();

		return ResponseEntity.created(location).build();
	}

	// Update existing category
	@PutMapping("/giftandgain/target/edit/{id}")
	public ResponseEntity<TargetInventory> updateTarget(@PathVariable Long id,
			@RequestBody TargetInventory updatedInventory) {
		Optional<TargetInventory> existingItem = targetRepo.findById(id);

		if (!existingItem.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
		}

		// Update the existing item's properties with the new values
		TargetInventory currentItem = existingItem.get();
		currentItem.setCategory(updatedInventory.getCategory());
		currentItem.setTargetQuantity(updatedInventory.getTargetQuantity());
		currentItem.setTargetMonthYear(updatedInventory.getTargetMonthYear());

		// Save the updated item
		TargetInventory updatedItem = targetRepo.save(currentItem);

		return ResponseEntity.ok(updatedItem);
	}

	// Delete selected category
	@DeleteMapping("/giftandgain/target/delete/{id}")
	public ResponseEntity<Void> deleteTarget(@PathVariable Long id) {
		try {
			targetRepo.deleteById(id);
			return ResponseEntity.noContent().build(); // HTTP 204 No Content
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build(); // HTTP 404 Not Found
		}
	}

	// Called by report microservice
	@GetMapping("/giftandgain/target/generate-report")
	public ResponseEntity<String> generateReport(@RequestParam int month, @RequestParam int year) {
		List<Object[]> totalQuantitiesByDate = targetRepo.getTotalQuantitiesByDate(month, year);
	
		StringBuilder reportCSV = new StringBuilder();
		String[] header = {"Category", "Unit", "Received Quantity", "Target Quantity"};
		reportCSV.append(String.join(",", header)).append("\n");
	
		for (Object[] row : totalQuantitiesByDate) {
			Category category = (Category) row[0];
			String unit = (String) row[1];
			BigDecimal receivedQuantity = (BigDecimal) row[2];
			BigDecimal targetQuantity = (BigDecimal) row[3];
	
			reportCSV.append(category.getCategory()).append(",");
			reportCSV.append(unit).append(",");
			reportCSV.append(receivedQuantity).append(",");
			reportCSV.append(targetQuantity).append("\n");
		}
	
		byte[] csvData = reportCSV.toString().getBytes();
	
		// Encode the CSV data as a base64 string
		String base64Csv = Base64.getEncoder().encodeToString(csvData);
	
		// Return the base64-encoded CSV as a string in the response body
		return ResponseEntity.ok(base64Csv);
	}

}
