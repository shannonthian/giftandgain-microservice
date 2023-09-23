package com.giftandgain.report.controller;

import com.giftandgain.report.AmazonAws;
import com.giftandgain.report.manager.ReportManager;
import com.giftandgain.report.model.CustomResponse;
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
    private final ReportManager reportManager;

    @Autowired
    public ReportController(ReportRepository reportRepository, TargetInventoryRepository targetInventoryRepository, InventoryManagementRepository inventoryManagementRepository, AmazonAws amazonAws, ReportManager reportManager) {
        this.reportRepository = reportRepository;
        this.targetInventoryRepository = targetInventoryRepository;
        this.inventoryManagementRepository = inventoryManagementRepository;
        this.amazonAws = amazonAws;
        this.reportManager = reportManager;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<Report>> findByMonthAndYear(
        @RequestParam(required = false) String month,
        @RequestParam(required = false) String year
    ) {
        List<Report> reports;
    
        if (isValidMonth(month) && isValidYear(year)) {
            // Both month and year parameters are provided
            reports = reportRepository.findByMonthAndYear(month, year);
        } else if (isValidMonth(month)) {
            // Only month parameter is provided
            reports = reportRepository.findByMonth(month);
        } else if (isValidYear(year)) {
            // Only year parameter is provided
            reports = reportRepository.findByYear(year);
        } else {
            // Neither month nor year parameters are provided, fetch all reports
            reports = reportRepository.findAll();
        }
    
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping("/download")
	public ResponseEntity<CustomResponse> downloadReport(
        @RequestParam(required = false) String month,
        @RequestParam(required = false) String year
    ) {
        if (!isValidMonth(month) || !isValidYear(year)) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "text/plain");
            CustomResponse response = new CustomResponse();
            response.setMessage("Invalid month or year format.");
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        if (!amazonAws.doesBucketExist()) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "text/plain");
            CustomResponse response = new CustomResponse();
            response.setMessage("Bucket does not exist.");
        }
        
        String objectKey = "InventoryReport_" + month + "_" + year + ".csv";       

        if (amazonAws.listObjects().contains(objectKey)) {
            String objectUrl = amazonAws.getObjectUrl(objectKey);
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
            CustomResponse response = new CustomResponse();
            response.setMessage("Retrieved existing report.");
            response.setPayload(objectUrl);
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        }

        byte[] csvData = reportManager.generateCsv(month, year);
        amazonAws.uploadFile(objectKey, csvData);

        String objectUrl = amazonAws.getObjectUrl(objectKey);

        reportRepository.save(new Report(month, year, objectUrl));
	   
	    HttpHeaders headers = new HttpHeaders();
	    headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

        CustomResponse response = new CustomResponse();
        response.setMessage("Successfully uploaded report.");
        response.setPayload(objectUrl);
	    return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

    private boolean isValidMonth(String month) {
        return month != null && month.length() == 2 && isInteger(month);
    }

    private boolean isValidYear(String year) {
        return year != null && year.length() == 4 && isInteger(year);
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
