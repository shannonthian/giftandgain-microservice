package com.giftandgain.inventorymanagement.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
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

import com.giftandgain.inventorymanagement.entity.InventoryManagement;
import com.giftandgain.inventorymanagement.entity.TargetInventory;
import com.giftandgain.inventorymanagement.repository.TargetInventoryRepository;

@RestController
public class TargetInventoryController {

	private TargetInventoryRepository targetRepo;

	public TargetInventoryController(TargetInventoryRepository targetRepo) {
		this.targetRepo = targetRepo;
	}

	// Get all the category with pagination and sorting
	@GetMapping("/giftandgain/category")
	public List<TargetInventory> getCategoryList(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {

		Pageable pageable;

		// Check if sortBy parameter is provided
		if (sortBy != null && !sortBy.trim().isEmpty()) {
			if ("desc".equalsIgnoreCase(direction)) {
				pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
			} else {
				pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
			}
		} else {
			pageable = PageRequest.of(page, size); // No sorting
		}

		return targetRepo.findAll(pageable).getContent();
	}

	// Get selected category
	@GetMapping("/giftandgain/category/{id}")
	public ResponseEntity<TargetInventory> retrieveItem(@PathVariable Long id) {
		Optional<TargetInventory> inventoryManagement = targetRepo.findById(id);

		if (inventoryManagement.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
		}

		return new ResponseEntity<>(inventoryManagement.get(), HttpStatus.OK);
	}

	// Create new category
	@PostMapping("/giftandgain/category")
	public ResponseEntity<TargetInventory> createItem(@RequestBody TargetInventory targetInventory) {

		// Save the record with the current date as the createdDate
		TargetInventory savedItem = targetRepo.save(targetInventory);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedItem.getTargetId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
	
	// Update existing category
	@PutMapping("/giftandgain/category/{id}")
	public ResponseEntity<TargetInventory> updateItem(@PathVariable Long id, @RequestBody TargetInventory updatedInventory) {
	    Optional<TargetInventory> existingItem = targetRepo.findById(id);

	    if (existingItem.isEmpty()) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
	    }

	    // Update the existing item's properties with the new values
	    TargetInventory currentItem = existingItem.get();
	    currentItem.setCategory(updatedInventory.getCategory());
	    currentItem.setUnit(updatedInventory.getUnit());
	    currentItem.setTargetQuantity(updatedInventory.getTargetQuantity());
	    currentItem.setTargetMonth(updatedInventory.getTargetMonth());

	    // Save the updated item
	    TargetInventory updatedItem = targetRepo.save(currentItem);

	    return ResponseEntity.ok(updatedItem);
	}

	// Delete selected category
	@DeleteMapping("/giftandgain/category/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
		try {
			targetRepo.deleteById(id);
			return ResponseEntity.noContent().build(); // HTTP 204 No Content
		} catch (EmptyResultDataAccessException e) {
			// If the item with the provided id does not exist, you might want to handle it
			// here
			return ResponseEntity.notFound().build(); // HTTP 404 Not Found
		}
	}
	
	// Download selected report
	@GetMapping("giftandgain/download/report/{month}/{year}")
	public ResponseEntity<byte[]> downloadReport(@PathVariable String month, @PathVariable String year) {
	    
	    // 1. Fetch the data
	    List<Object[]> monthlyReport = targetRepo.getTotalQuantitiesByDate(month, year);
	    
	    // 2. Convert the report data to CSV format
	    StringBuilder reportCSV = new StringBuilder();
	    String[] header = {"Category", "Unit", "Received Quantity", "Target Quantity"};
	    reportCSV.append(String.join(",", header)).append("\n"); // Added this line to include header in CSV

	    for (Object[] row : monthlyReport) {
	        String category = (String) row[0];
	        String unit = (String) row[1];
	        Long receivedQuantity = (Long) row[2];
	        Integer targetQuantity = (Integer) row[3];

	        reportCSV.append(category).append(",");
	        reportCSV.append(unit).append(",");
	        reportCSV.append(receivedQuantity).append(",");
	        reportCSV.append(targetQuantity).append("\n");
	    }

	    byte[] csvData = reportCSV.toString().getBytes();

	    // 3. Set headers and return the file
	    HttpHeaders headers = new HttpHeaders();
	    headers.set(HttpHeaders.CONTENT_TYPE, "text/plain");
	    headers.setContentDispositionFormData("attachment", "InventoryReport_" + month + "_" + year + ".csv");

	    return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
	}


}

