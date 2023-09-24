package com.giftandgain.report;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.giftandgain.report.model.InventoryManagement;
import com.giftandgain.report.model.TargetInventory;
import com.giftandgain.report.repository.InventoryManagementRepository;
import com.giftandgain.report.repository.TargetInventoryRepository;
@SpringBootApplication
public class ReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
	}

	    @Bean
    CommandLineRunner inventoryCommandLineRunner(InventoryManagementRepository inventoryManagementRepository) {
        return args -> {
            inventoryManagementRepository.save(new InventoryManagement("Rice", "Grains", "kg", 200, LocalDate.of(2025, 9, 10), LocalDateTime.now(), "this is a dry food"));
            inventoryManagementRepository.save(new InventoryManagement("Noodles", "Grains", "kg", 30, LocalDate.of(2025, 9, 10), LocalDateTime.parse("2023-09-10T22:12:16.524557"), "this is a dry food"));
            inventoryManagementRepository.save(new InventoryManagement("Tuna Can", "Canned Food", "counts", 10, LocalDate.of(2025, 9, 10), LocalDateTime.parse("2022-09-10T22:12:16.524557"), "this is a dry food"));
            inventoryManagementRepository.save(new InventoryManagement("Milk", "Beverages", "ml", 300, LocalDate.of(2025, 9, 10), LocalDateTime.parse("2023-09-10T22:12:16.524557"), "this is a dry food"));
            inventoryManagementRepository.save(new InventoryManagement("Frozen Strawberries", "Frozen Food", "packs", 10, LocalDate.of(2025, 9, 10), LocalDateTime.parse("2023-09-10T22:12:16.524557"), "this is a dry food"));
            inventoryManagementRepository.save(new InventoryManagement("Milo Drinks", "Beverages", "ml", 300, LocalDate.of(2025, 9, 10), LocalDateTime.parse("2023-10-10T22:12:16.524557"), "this is a dry food"));
        };
    }

    @Bean
    CommandLineRunner targetInventoryCommandLineRunner(TargetInventoryRepository targetInventoryRepository) {
        return args -> {
            targetInventoryRepository.save(new TargetInventory("Grains", "kg", 100, "09", "2023"));
            targetInventoryRepository.save(new TargetInventory("Frozen food", "packs", 350, "09", "2023"));
            targetInventoryRepository.save(new TargetInventory("Beverages", "ml", 250, "09", "2023"));
        };
    }

}
