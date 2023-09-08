package com.giftandgain.inventorymanagement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryManagementRepository extends JpaRepository<InventoryManagement, Long> {

	@Query("SELECT i FROM tb_inventory i WHERE MONTH(i.createdDate) = ?1 AND YEAR(i.createdDate) = ?2")
	List<InventoryManagement> findByCreatedDate(int month, int year);

}
