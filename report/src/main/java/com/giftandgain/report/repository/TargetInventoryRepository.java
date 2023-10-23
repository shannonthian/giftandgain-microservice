package com.giftandgain.report.repository;

import com.giftandgain.report.model.InventoryReportDTO;
import com.giftandgain.report.model.TargetInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TargetInventoryRepository extends PagingAndSortingRepository<TargetInventory, Long>, JpaRepository<TargetInventory, Long>, JpaSpecificationExecutor<TargetInventory> {
//    @Query("SELECT i.category, c.unit, SUM(i.receivedQuantity), t.targetQuantity " +
//            "FROM tb_inventory i " +
//            "LEFT JOIN i.category c " +
//            "LEFT JOIN tb_target_inventory t ON c = t.category AND FUNCTION('MONTH', t.targetMonthYear) = :month AND FUNCTION('YEAR', t.targetMonthYear) = :year " +
//            "WHERE FUNCTION('MONTH', i.createdDate) = :month AND FUNCTION('YEAR', i.createdDate) = :year " +
//            "GROUP BY i.category, c.unit, t.targetQuantity")
//    List<Object[]> getTotalQuantitiesByDate(@Param("month") int month, @Param("year") int year);

    @Query("SELECT c.category, c.unit, SUM(t.targetQuantity) " +
            "FROM tb_target_inventory t " +
            "LEFT JOIN t.category c ON FUNCTION('MONTH', t.targetMonthYear) = :month " +
            "AND FUNCTION('YEAR', t.targetMonthYear) = :year " +
            "GROUP BY c.category, c.unit")
    List<Object[]> getTargetInventoryResults(@Param("month") int month, @Param("year") int year);

    @Query("SELECT c.category, SUM(i.receivedQuantity) " +
            "FROM tb_inventory i " +
            "JOIN i.category c " +
            "WHERE FUNCTION('MONTH', i.createdDate) = :month " +
            "AND FUNCTION('YEAR', i.createdDate) = :year " +
            "GROUP BY c.category")
    List<Object[]> getInventoryManagementResults(@Param("month") int month, @Param("year") int year);

//    @Query("SELECT new com.giftandgain.report.model.InventoryReportDTO(c.category, c.unit, SUM(ti.targetQuantity), SUM(im.receivedQuantity)) " +
//            "FROM tb_category c " +
//            "LEFT JOIN c.targetInventories ti " +
//            "LEFT JOIN c.inventories im " +
//            "WHERE FUNCTION('YEAR', ti.targetMonthYear) = :year AND FUNCTION('MONTH', ti.targetMonthYear) = :month " +
//            "AND FUNCTION('YEAR', im.createdDate) = :year AND FUNCTION('MONTH', im.createdDate) = :month " +
//            "GROUP BY c.category, c.unit")
//    List<InventoryReportDTO> generateReport(@Param("month") int month, @Param("year") int year);

}
