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

}
