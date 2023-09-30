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
import com.giftandgain.inventorymanagement.repository.InventoryManagementRepository;



@RestController
public class InventoryManagementController {
	
	private InventoryManagementRepository inventoryRepo;
	
	public InventoryManagementController(InventoryManagementRepository inventoryRepo) {
		this.inventoryRepo = inventoryRepo;
	}

	// Get all the items with pagination and sorting
	@GetMapping("/giftandgain/items")
	public List<InventoryManagement> getInventoryList(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(required = false) String sortBy,
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
	        pageable = PageRequest.of(page, size);  // No sorting
	    }

	    return inventoryRepo.findAll(pageable).getContent();
	}

	
	// Get selected item
	@GetMapping("/giftandgain/items/{id}")
	public ResponseEntity<InventoryManagement> retrieveItem(@PathVariable Long id) {
	    Optional<InventoryManagement> inventoryManagement = inventoryRepo.findById(id);

	    if (!inventoryManagement.isPresent()) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
	    }

	    return new ResponseEntity<>(inventoryManagement.get(), HttpStatus.OK);
	}
	
	// Create new item
	@PostMapping("/giftandgain/items")
	public ResponseEntity<InventoryManagement> createItem(@RequestBody InventoryManagement inventoryManagement) {
	    // Set the createdDate to the current date and time
	    inventoryManagement.setCreatedDateToNow();
	    
	    // Save the record with the current date as the createdDate
	    InventoryManagement savedItem = inventoryRepo.save(inventoryManagement);
	    
	    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedItem.getInventoryId()).toUri();
	    
	    return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/giftandgain/items/{id}")
    public ResponseEntity<InventoryManagement> editItem(@PathVariable Long id, @RequestBody InventoryManagement updatedInventory) {
        Optional<InventoryManagement> inventoryOpt = inventoryRepo.findById(id);

        if (!inventoryOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
        }

        InventoryManagement currentInventory = inventoryOpt.get();
        currentInventory.setItemName(updatedInventory.getItemName());
        currentInventory.setCategory(updatedInventory.getCategory());
        currentInventory.setUnit(updatedInventory.getUnit());
        currentInventory.setReceivedQuantity(updatedInventory.getReceivedQuantity());
        currentInventory.setExpiryDate(updatedInventory.getExpiryDate());
        currentInventory.setCreatedDateToNow();
        inventoryRepo.save(currentInventory);

        return new ResponseEntity<>(currentInventory, HttpStatus.OK);
    }

	
	// Delete selected item
	@DeleteMapping("/giftandgain/items/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
	    try {
	        inventoryRepo.deleteById(id);
	        return ResponseEntity.noContent().build(); // HTTP 204 No Content
	    } catch (EmptyResultDataAccessException e) {
	        // If the item with the provided id does not exist, you might want to handle it here
	        return ResponseEntity.notFound().build(); // HTTP 404 Not Found
	    }
	}
	
	// Get high priority category 
	@GetMapping("giftandgain/category/highpriority/{month}/{year}")
	public ResponseEntity<List<String>> getHighPriorityCategories(@PathVariable String month, @PathVariable String year) {
	    List<String> highPriorityCategories = inventoryRepo.getHighPriorityList(month,year);
	    return new ResponseEntity<>(highPriorityCategories, HttpStatus.OK);
	}
	
	// Get low priority category 
		@GetMapping("giftandgain/category/lowpriority/{month}/{year}")
		public ResponseEntity<List<String>> getLowPriorityCategories(@PathVariable String month, @PathVariable String year) {
		    List<String> lowPriorityCategories = inventoryRepo.getLowPriorityList(month,year);
		    return new ResponseEntity<>(lowPriorityCategories, HttpStatus.OK);
		}
	
	
}