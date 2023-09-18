package com.giftandgain.inventorymanagement.specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.giftandgain.inventorymanagement.entity.InventoryManagement;

public class InventoryManagementSpecification {
	
	public static Specification<InventoryManagement> hasItemName(String itemName) {
        return (root, cq, cb) -> itemName == null ? null : cb.equal(root.get("itemName"), itemName);
    }

    public static Specification<InventoryManagement> hasCategory(String category) {
        return (root, cq, cb) -> category == null ? null : cb.equal(root.get("category"), category);
    }

    public static Specification<InventoryManagement> hasReceivedQuantity(BigDecimal receivedQuantity) {
        return (root, cq, cb) -> receivedQuantity == null ? null : cb.equal(root.get("receivedQuantity"), receivedQuantity);
    }

//    public static Specification<InventoryManagement> hasExpiryDate(LocalDate expiryDate) {
//        return (root, cq, cb) -> expiryDate == null ? null : cb.equal(root.get("expiryDate"), expiryDate);
//    }
    
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

    
    public static Specification<InventoryManagement> hasCreatedDate(LocalDate createdDate) {
        return (root, cq, cb) -> createdDate == null ? null : cb.equal(root.get("createdDate"), createdDate);
    }
}
