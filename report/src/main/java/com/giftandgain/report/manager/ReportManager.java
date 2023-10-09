package com.giftandgain.report.manager;

import com.giftandgain.report.model.InventoryManagement;
import com.giftandgain.report.model.TargetInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Configuration
public class ReportManager {
    private final RestTemplate restTemplate;

    @Autowired
    public ReportManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateCsv(int month, int year) {
        String baseUrl = System.getenv("REPORT_GENERATION_BASE_URL");

        if (baseUrl == null) {
            throw new IllegalStateException("REPORT_GENERATION_BASE_URL environment variable is not set.");
        }

        String urlGenerateReport = baseUrl + "?month=" + month + "&year=" + year;

        String base64Csv = restTemplate.getForObject(urlGenerateReport, String.class);

        return base64Csv;
    }
}
