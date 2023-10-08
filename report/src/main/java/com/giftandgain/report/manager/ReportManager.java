package com.giftandgain.report.manager;

import java.util.List;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.giftandgain.report.repository.TargetInventoryRepository;

@Configuration
public class ReportManager {
    private final TargetInventoryRepository targetInventoryRepository;

    @Autowired
    public ReportManager(TargetInventoryRepository targetInventoryRepository) {
    this.targetInventoryRepository = targetInventoryRepository;
    }

    public String generateCsv(int month, int year) {
         // 1. Fetch the data
	    List<Object[]> monthlyReport = targetInventoryRepository.getTotalQuantitiesByDate(month, year);
	    
	    // 2. Convert the report data to CSV format
	    StringBuilder reportCSV = new StringBuilder();
	    String[] header = {"Category", "Unit", "Received Quantity", "Target Quantity"};
	    reportCSV.append(String.join(",", header)).append("\n"); // Added this line to include header in CSV

	    for (Object[] row : monthlyReport) {
	        String category = (String) row[0];
	        String unit = (String) row[1];
	        Long receivedQuantity = (Long) row[2];
	        Integer targetQuantity = (Integer) row[3];

	        reportCSV.append(category).append(",");
	        reportCSV.append(unit).append(",");
	        reportCSV.append(receivedQuantity).append(",");
	        reportCSV.append(targetQuantity).append("\n");
	    }

	    byte[] csvData = reportCSV.toString().getBytes();

		// 3. Encode the CSV data as a base64 string
		String base64Csv = Base64.getEncoder().encodeToString(csvData);
        return base64Csv;
    }
}
