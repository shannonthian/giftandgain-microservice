package com.giftandgain.inventorymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giftandgain.inventorymanagement.entity.InventoryManagement;

public interface InventoryManagementRepository extends JpaRepository<InventoryManagement, Long> {
	
	@Query("SELECT i.category " +
		       "FROM tb_inventory i " +
		       "JOIN tb_target_inventory t ON i.category = t.category AND t.targetMonth = :month AND t.targetYear = :year " +
		       "WHERE FUNCTION('MONTH', i.createdDate) = :month AND FUNCTION('YEAR', i.createdDate) = :year " +
		       "GROUP BY i.category, t.targetQuantity " +
		       "HAVING SUM(i.receivedQuantity) < t.targetQuantity")
		List<String> getHighPriorityList(@Param("month") String month, @Param("year") String year);

	
	@Query("SELECT i.category " +
		       "FROM tb_inventory i " +
		       "JOIN tb_target_inventory t ON i.category = t.category AND t.targetMonth = :month AND t.targetYear = :year " +
		       "WHERE FUNCTION('MONTH', i.createdDate) = :month AND FUNCTION('YEAR', i.createdDate) = :year " +
		       "GROUP BY i.category, t.targetQuantity " +
		       "HAVING SUM(i.receivedQuantity) > t.targetQuantity")
		List<String> getLowPriorityList(@Param("month") String month, @Param("year") String year);
	

}
