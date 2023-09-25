package com.giftandgain.inventorymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.giftandgain.inventorymanagement.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    boolean existsByCategoryAndUnitAndCategoryIdNot(String category, String unit, Long id);

	boolean existsByCategoryAndUnit(String category, String unit);

	int countByCategoryAndStatusAndCategoryIdNot(String category, String status, Long id);


}
