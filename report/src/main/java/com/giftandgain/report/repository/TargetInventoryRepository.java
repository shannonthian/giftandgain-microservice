package com.giftandgain.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giftandgain.report.model.TargetInventory;

public interface TargetInventoryRepository extends JpaRepository<TargetInventory, Long> {
	
	@Query("SELECT i.category, t.unit, SUM(i.receivedQuantity), t.targetQuantity " +
	"FROM tb_inventory i " +
	"LEFT JOIN tb_target_inventory t ON i.category = t.category AND t.targetMonth = :month AND t.targetYear = :year " +
	"WHERE EXTRACT(MONTH FROM i.createdDate) = CAST(:month AS integer) AND EXTRACT(YEAR FROM i.createdDate) = CAST(:year AS integer) " +
	"GROUP BY i.category, t.unit, t.targetQuantity")
	List<Object[]> getTotalQuantitiesByDate(@Param("month") String month, @Param("year") String year);
}
