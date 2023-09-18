package com.giftandgain.inventorymanagement.specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.giftandgain.inventorymanagement.entity.TargetInventory;

public class TargetInventorySpecification {

	public static Specification<TargetInventory> hasCategory(String category) {
		return (root, cq, cb) -> category == null ? null : cb.equal(root.get("category"), category);
	}

	public static Specification<TargetInventory> hasUnit(String unit) {
		return (root, cq, cb) -> unit == null ? null : cb.equal(root.get("unit"), unit);
	}

	public static Specification<TargetInventory> hasTargetQuantity(BigDecimal targetQuantity) {
		return (root, cq, cb) -> targetQuantity == null ? null : cb.equal(root.get("targetQuantity"), targetQuantity);
	}

	public static Specification<TargetInventory> hasTargetMonthYear(LocalDate targetMonthYear) {
		return (root, cq, cb) -> targetMonthYear == null ? null
				: cb.equal(root.get("targetMonthYear"), targetMonthYear);
	}

}
