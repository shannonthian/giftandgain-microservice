package com.giftandgain.inventorymanagement;

import com.giftandgain.inventorymanagement.entity.Category;
import com.giftandgain.inventorymanagement.entity.InventoryManagement;
import com.giftandgain.inventorymanagement.entity.TargetInventory;
import com.giftandgain.inventorymanagement.repository.CategoryRepository;
import com.giftandgain.inventorymanagement.repository.InventoryManagementRepository;
import com.giftandgain.inventorymanagement.repository.TargetInventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class InventoryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner run(CategoryRepository categoryRepository, InventoryManagementRepository inventoryManagementRepository, TargetInventoryRepository targetInventoryRepository) {
		return args -> {
			// Category biscuitCategory = new Category(1L, "Biscuit", "Packet", "active");
			// Category milkCategory = new Category(2L, "Milk", "Litre", "active");
			// categoryRepository.save(biscuitCategory);
			// categoryRepository.save(milkCategory);

			// inventoryManagementRepository.save(new InventoryManagement(1L, "Kong Guan Special Biscuits", biscuitCategory, new BigDecimal(10),
			// 		LocalDate.of(2023, 12, 1), LocalDate.now(), "remarks1", ""));
			// inventoryManagementRepository.save(new InventoryManagement(2L, "Fairprice milk 950ML", milkCategory, new BigDecimal(1),
			// 		LocalDate.of(2023, 11, 21), LocalDate.now(), "remarks2", ""));

			// targetInventoryRepository.save(new TargetInventory(1L, biscuitCategory, new BigDecimal(100), LocalDate.of(2023, 9, 1)));
		};
	}
}
