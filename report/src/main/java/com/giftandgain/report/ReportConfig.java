package com.giftandgain.report;

import com.giftandgain.report.model.InventoryManagement;
import com.giftandgain.report.model.Report;
import com.giftandgain.report.model.TargetInventory;
import com.giftandgain.report.repository.InventoryManagementRepository;
import com.giftandgain.report.repository.ReportRepository;
import com.giftandgain.report.repository.TargetInventoryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportConfig {

    // This will run when application is started
    // e.g. to seed data
    @Bean
    CommandLineRunner reportCommandLineRunner(ReportRepository reportRepository) {
        return args -> {
            reportRepository.save(new Report("09", "2023", "test_url_1"));
            reportRepository.save(new Report("10", "2023", "test_url_2"));
        };
    }

            @Bean
    CommandLineRunner inventoryCommandLineRunner(InventoryManagementRepository inventoryManagementRepository) {
        return args -> {
            inventoryManagementRepository.save(new InventoryManagement("Rice", "Grains", "kg", 200, LocalDate.of(2023, 9, 1), LocalDateTime.now(), "remarks 1"));
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
