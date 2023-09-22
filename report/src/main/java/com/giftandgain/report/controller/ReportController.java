package com.giftandgain.report.controller;

import com.giftandgain.report.AmazonAws;
import com.giftandgain.report.model.InventoryManagement;
import com.giftandgain.report.model.Report;
import com.giftandgain.report.model.TargetInventory;
import com.giftandgain.report.repository.InventoryManagementRepository;
import com.giftandgain.report.repository.ReportRepository;
import com.giftandgain.report.repository.TargetInventoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/report")
public class ReportController {

    private final ReportRepository reportRepository;
    private final TargetInventoryRepository targetInventoryRepository;
    private final InventoryManagementRepository inventoryManagementRepository;
    private final AmazonAws amazonAws;

    @Autowired
    public ReportController(ReportRepository reportRepository, TargetInventoryRepository targetInventoryRepository, InventoryManagementRepository inventoryManagementRepository, AmazonAws amazonAws) {
        this.reportRepository = reportRepository;
        this.targetInventoryRepository = targetInventoryRepository;
        this.inventoryManagementRepository = inventoryManagementRepository;
        this.amazonAws = amazonAws;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<Report>> findByMonthAndYear(
        @RequestParam(required = false) String month,
        @RequestParam(required = false) String year
    ) {
        List<Report> reports;
    
        if (month != null && year != null) {
            // Both month and year parameters are provided
            reports = reportRepository.findByMonthAndYear(month, year);
        } else if (month != null) {
            // Only month parameter is provided
            reports = reportRepository.findByMonth(month);
        } else if (year != null) {
            // Only year parameter is provided
            reports = reportRepository.findByYear(year);
        } else {
            // Neither month nor year parameters are provided, fetch all reports
            reports = reportRepository.findAll();
        }
    
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping("/inventory")
    public @ResponseBody ResponseEntity<List<InventoryManagement>> getAllInventory() {
        List<InventoryManagement> inventoryList= inventoryManagementRepository.findAll();
        return new ResponseEntity<>(inventoryList, HttpStatus.OK);
    }

        @GetMapping("/target-inventory")
    public @ResponseBody ResponseEntity<List<TargetInventory>> getAllTargetInventory() {
        List<TargetInventory> targetInventoryList= targetInventoryRepository.findAll();
        return new ResponseEntity<>(targetInventoryList, HttpStatus.OK);
    }

    @GetMapping("/download/{month}/{year}")
	public ResponseEntity<byte[]> downloadReport(@PathVariable String month, @PathVariable String year) {        
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

	    // 3. Set headers and return the file
	    HttpHeaders headers = new HttpHeaders();
        String objectKey = "InventoryReport_" + month + "_" + year + ".csv";
	    headers.set(HttpHeaders.CONTENT_TYPE, "text/plain");
	    headers.setContentDispositionFormData("attachment", objectKey);

        // TODO: enable when bucket is up
        // if (amazonAws.doesBucketExist()) {
        //     amazonAws.uploadFile(objectKey, csvData);
        // }

	    return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
	}
}
