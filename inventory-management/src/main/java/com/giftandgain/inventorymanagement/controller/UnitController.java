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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.giftandgain.inventorymanagement.entity.Unit;
import com.giftandgain.inventorymanagement.repository.UnitRepository;

@RestController
public class UnitController {

	private final UnitRepository unitRepo;

	public UnitController(UnitRepository unitRepo) {
		this.unitRepo = unitRepo;
	}

	@GetMapping("/giftandgain/unit")
	public ResponseEntity<List<Unit>> getUnitList(@RequestParam(defaultValue = "0") int page,
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

		Page<Unit> result = unitRepo.findAll(pageable);
		long totalItems = result.getTotalElements();

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("x-total-count", String.valueOf(totalItems));

		return ResponseEntity.ok().headers(responseHeaders).body(result.getContent());
	}

	@GetMapping("/giftandgain/unit/{id}")
	public ResponseEntity<Unit> retrieveUnit(@PathVariable Long id) {
		Optional<Unit> unit = unitRepo.findById(id);
		if (unit.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
		}
		return new ResponseEntity<>(unit.get(), HttpStatus.OK);
	}

	@PostMapping("/giftandgain/unit/create")
	public ResponseEntity<Void> createUnit(@RequestBody Unit unit) {

		if (unitRepo.existsByUnit(unit.getUnit())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit already exists: " + unit.getUnit());
		}

		Unit savedUnit = unitRepo.save(unit);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUnit.getUnitId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/giftandgain/unit/edit/{id}")
	public ResponseEntity<Unit> updateUnit(@PathVariable Long id, @RequestBody Unit updatedUnit) {
		Optional<Unit> existingUnit = unitRepo.findById(id);

		if (existingUnit.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
		}
		
		if (unitRepo.existsByUnitAndUnitIdNot(updatedUnit.getUnit(), id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Another unit with the name exists: " + updatedUnit.getUnit());
		}

		Unit currentUnit = existingUnit.get();
		currentUnit.setUnit(updatedUnit.getUnit());
		currentUnit.setDescription(updatedUnit.getDescription());

		Unit updatedUnits = unitRepo.save(currentUnit);

		return ResponseEntity.ok(updatedUnits);
	}

	@DeleteMapping("/giftandgain/unit/delete/{id}")
	public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
		try {
			unitRepo.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
