package com.giftandgain.inventorymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.giftandgain.inventorymanagement.entity.TargetInventory;

public interface TargetInventoryRepository extends PagingAndSortingRepository<TargetInventory, Long>, JpaRepository<TargetInventory, Long>, JpaSpecificationExecutor<TargetInventory> {
	@Query("SELECT i.category, c.unit, SUM(i.receivedQuantity), t.targetQuantity " +
		       "FROM tb_inventory i " +
		       "LEFT JOIN i.category c " +
		       "LEFT JOIN tb_target_inventory t ON c = t.category AND FUNCTION('MONTH', t.targetMonthYear) = :month AND FUNCTION('YEAR', t.targetMonthYear) = :year " +
		       "WHERE FUNCTION('MONTH', i.createdDate) = :month AND FUNCTION('YEAR', i.createdDate) = :year " +
		       "GROUP BY i.category, c.unit, t.targetQuantity")
		List<Object[]> getTotalQuantitiesByDate(@Param("month") int month, @Param("year") int year);



}
