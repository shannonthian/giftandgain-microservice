package com.giftandgain.report.manager;

import com.giftandgain.report.model.Category;
import com.giftandgain.report.model.InventoryManagement;
import com.giftandgain.report.model.InventoryReportDTO;
import com.giftandgain.report.model.TargetInventory;
import com.giftandgain.report.repository.TargetInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
public class ReportManager {
    private final RestTemplate restTemplate;

    private final TargetInventoryRepository targetInventoryRepository;

    @Autowired
    public ReportManager(RestTemplate restTemplate, TargetInventoryRepository targetInventoryRepository) {
        this.restTemplate = restTemplate;
        this.targetInventoryRepository = targetInventoryRepository;
    }

    public List<InventoryReportDTO> generateReport(int month, int year) {
        List<Object[]> targetInventoryResults = targetInventoryRepository.getTargetInventoryResults(month, year);
        List<Object[]> inventoryManagementResults = targetInventoryRepository.getInventoryManagementResults(month, year);

        Map<String, BigDecimal> inventoryByCategory = new HashMap<>();
        for (Object[] inventoryResult : inventoryManagementResults) {
            String category = (String) inventoryResult[0]; // Assuming index 0 contains the Category
            BigDecimal receivedQuantity = (BigDecimal) inventoryResult[1]; // Assuming index 1 contains receivedQuantity
            inventoryByCategory.put(category, receivedQuantity);
        }

        List<InventoryReportDTO> combinedResults = new ArrayList<>();

        for (Object[] targetInventoryResult : targetInventoryResults) {
            String category = (String) targetInventoryResult[0]; // Assuming index 0 contains the Category
            String unit = (String) targetInventoryResult[1]; // Assuming index 1 contains the unit
            BigDecimal targetQuantity = (BigDecimal) targetInventoryResult[2]; // Assuming index 2 contains targetQuantity
            BigDecimal receivedQuantity = inventoryByCategory.get(category);

            if (receivedQuantity == null) {
                receivedQuantity = BigDecimal.ZERO;
            }

            combinedResults.add(new InventoryReportDTO(category, unit, targetQuantity, receivedQuantity));
        }

        return combinedResults;
    }

    public String generateCsv(int month, int year) {
        System.out.println("generateCsv() :: START");
        List<InventoryReportDTO> report = generateReport(month, year);
        System.out.println("report" + report);

        StringBuilder reportCSV = new StringBuilder();
        String[] header = {"Category", "Unit", "Received Quantity", "Target Quantity"};
        reportCSV.append(String.join(",", header)).append("\n");

        for (InventoryReportDTO row : report) {
            String category = (String) row.getCategory();
            String unit = (String) row.getUnit();
            BigDecimal receivedQuantity = (BigDecimal) row.getReceivedQuantity();
            BigDecimal targetQuantity = (BigDecimal) row.getTargetQuantity();

            reportCSV.append(category).append(",");
            reportCSV.append(unit).append(",");
            reportCSV.append(receivedQuantity).append(",");
            reportCSV.append(targetQuantity).append("\n");
        }

        byte[] csvData = reportCSV.toString().getBytes();

        String base64CsvString = Base64.getEncoder().encodeToString(csvData);

        System.out.println("generateCsv() :: END");
        return base64CsvString;
    }
}
