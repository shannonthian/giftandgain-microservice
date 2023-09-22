package com.giftandgain.inventorymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.giftandgain.inventorymanagement.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category>{

	// This method will check if a category with the given name exists
    boolean existsByCategory(String categoryName);
    
    boolean existsByCategoryAndCategoryIdNot(String category, Long categoryId);

}
