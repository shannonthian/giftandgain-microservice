package com.giftandgain.report.manager;

import com.giftandgain.report.model.Category;
import com.giftandgain.report.model.InventoryManagement;
import com.giftandgain.report.model.TargetInventory;
import com.giftandgain.report.repository.TargetInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

@Configuration
public class ReportManager {
    private final RestTemplate restTemplate;

    private final TargetInventoryRepository targetInventoryRepository;

    @Autowired
    public ReportManager(RestTemplate restTemplate, TargetInventoryRepository targetInventoryRepository) {
        this.restTemplate = restTemplate;
        this.targetInventoryRepository = targetInventoryRepository;
    }

    public String generateCsv(int month, int year) {
        System.out.println("generateCsv() :: START");
        List<Object[]> totalQuantitiesByDate = targetInventoryRepository.getTotalQuantitiesByDate(month, year);

        StringBuilder reportCSV = new StringBuilder();
        String[] header = {"Category", "Unit", "Received Quantity", "Target Quantity"};
        reportCSV.append(String.join(",", header)).append("\n");

        for (Object[] row : totalQuantitiesByDate) {
            Category category = (Category) row[0];
            String unit = (String) row[1];
            BigDecimal receivedQuantity = (BigDecimal) row[2];
            BigDecimal targetQuantity = (BigDecimal) row[3];

            reportCSV.append(category.getCategory()).append(",");
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
