package com.giftandgain.inventorymanagement.specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.giftandgain.inventorymanagement.entity.TargetInventory;

public class TargetInventorySpecification {

	public static Specification<TargetInventory> hasCategory(Long categoryId) {
		return (root, cq, cb) -> {
			if (categoryId != null) {
				return cb.equal(root.get("category").get("id"), categoryId);
			} else {
				return null;
			}
		};
	}

	public static Specification<TargetInventory> hasTargetQuantity(BigDecimal targetQuantity) {
		return (root, cq, cb) -> targetQuantity == null ? null : cb.equal(root.get("targetQuantity"), targetQuantity);
	}

	public static Specification<TargetInventory> hasTargetMonthYear(LocalDate targetMonthYear) {
		return (root, cq, cb) -> targetMonthYear == null ? null
				: cb.equal(root.get("targetMonthYear"), targetMonthYear);
	}

}
