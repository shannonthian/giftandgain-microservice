package com.giftandgain.inventorymanagement.specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.giftandgain.inventorymanagement.entity.InventoryManagement;

public class InventoryManagementSpecification {

	public static Specification<InventoryManagement> hasItemName(String itemName) {
		return (root, cq, cb) -> {
			if (itemName != null && !itemName.isEmpty()) {
				return cb.like(cb.lower(root.get("itemName")), "%" + itemName.toLowerCase() + "%");
			} else {
				return null;
			}
		};
	}

	public static Specification<InventoryManagement> hasCategory(Long categoryId) {
		return (root, cq, cb) -> {
			if (categoryId != null) {
				return cb.equal(root.get("category").get("categoryId"), categoryId);
			} else {
				return null;
			}
		};
	}

	public static Specification<InventoryManagement> hasReceivedQuantity(BigDecimal receivedQuantity) {
		return (root, cq, cb) -> receivedQuantity == null ? null
				: cb.equal(root.get("receivedQuantity"), receivedQuantity);
	}
	
	public static Specification<InventoryManagement> hasCreatedBy(String createdBy) {
		return (root, cq, cb) -> {
			if (createdBy != null && !createdBy.isEmpty()) {
				return cb.like(cb.lower(root.get("createdBy")), "%" + createdBy.toLowerCase() + "%");
			} else {
				return null;
			}
		};
	}

	public static Specification<InventoryManagement> isExpiryDateBetween(LocalDate startDate, LocalDate endDate) {
		return (root, cq, cb) -> {
			if (startDate != null && endDate != null) {
				return cb.between(root.get("expiryDate"), startDate, endDate);
			} else if (startDate != null) {
				return cb.greaterThanOrEqualTo(root.get("expiryDate"), startDate);
			} else if (endDate != null) {
				return cb.lessThanOrEqualTo(root.get("expiryDate"), endDate);
			} else {
				return null;
			}
		};
	}

	public static Specification<InventoryManagement> isCreatedDateBetween(LocalDate startDate, LocalDate endDate) {
		return (root, cq, cb) -> {
			if (startDate != null && endDate != null) {
				return cb.between(root.get("createdDate"), startDate, endDate);
			} else if (startDate != null) {
				return cb.greaterThanOrEqualTo(root.get("createdDate"), startDate);
			} else if (endDate != null) {
				return cb.lessThanOrEqualTo(root.get("createdDate"), endDate);
			} else {
				return null;
			}
		};
	}
}
