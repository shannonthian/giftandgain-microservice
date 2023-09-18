package com.giftandgain.inventorymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.giftandgain.inventorymanagement.entity.InventoryManagement;

public interface InventoryManagementRepository extends PagingAndSortingRepository<InventoryManagement, Long>, JpaSpecificationExecutor<InventoryManagement> {
	
	@Query("SELECT i.category " +
		       "FROM tb_inventory i " +
		       "JOIN tb_target_inventory t ON i.category = t.category AND FUNCTION('MONTH', t.targetMonthYear) = :month AND FUNCTION('YEAR', t.targetMonthYear) = :year " +
		       "WHERE FUNCTION('MONTH', i.createdDate) = :month AND FUNCTION('YEAR', i.createdDate) = :year " +
		       "GROUP BY i.category, t.targetQuantity " +
		       "HAVING SUM(i.receivedQuantity) < t.targetQuantity")
		List<String> getHighPriorityList(@Param("month") int month, @Param("year") int year);


	
	@Query("SELECT i.category " +
		       "FROM tb_inventory i " +
		       "JOIN tb_target_inventory t ON i.category = t.category AND FUNCTION('MONTH', t.targetMonthYear) = :month AND FUNCTION('YEAR', t.targetMonthYear) = :year " +
		       "WHERE FUNCTION('MONTH', i.createdDate) = :month AND FUNCTION('YEAR', i.createdDate) = :year " +
		       "GROUP BY i.category, t.targetQuantity " +
		       "HAVING SUM(i.receivedQuantity) > t.targetQuantity")
		List<String> getLowPriorityList(@Param("month") int month, @Param("year") int year);


}
