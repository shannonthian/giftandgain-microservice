package com.giftandgain.report.controller;

import com.giftandgain.report.manager.ReportManager;
import com.giftandgain.report.model.CustomResponse;
import com.giftandgain.report.model.Report;
import com.giftandgain.report.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path="/api/report")
public class ReportController {
    private final ReportManager reportManager;
    private final ReportRepository reportRepository;

    @Autowired
    public ReportController(ReportManager reportManager, ReportRepository reportRepository) {
        this.reportManager = reportManager;
        this.reportRepository = reportRepository;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<CustomResponse<Report>> findByMonthAndYear(
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String year
    ) {
        int parsedMonth;
        int parsedYear;

        try {
            parsedMonth = month != null ? Integer.parseInt(month) : -1;
            parsedYear = year != null ? Integer.parseInt(year) : -1;
        } catch (NumberFormatException ex) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
            CustomResponse<Report> response = new CustomResponse<>();
            response.setMessage("Invalid month or year format.");
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        List<Report> reports = Arrays.asList();

        if (parsedMonth == -1 && parsedYear == -1) {
            reports = reportRepository.findAll();
        } else if (parsedMonth != -1 && parsedYear == -1) {
            reports = reportRepository.findByMonth(parsedMonth);
        } else if (parsedYear != -1 && parsedMonth == -1) {
            reports = reportRepository.findByYear(parsedYear);
        } else if (parsedMonth != -1 && parsedYear != -1) {
            reports = reportRepository.findByMonthAndYear(parsedMonth, parsedYear);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        CustomResponse<Report> response = new CustomResponse<>();
        response.setMessage("Reports retrieved.");
        response.setPayload(reports);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }


    @GetMapping("/download")
	public ResponseEntity<CustomResponse<String>> downloadReport(
        @RequestParam(required = false) String month,
        @RequestParam(required = false) String year
    ) {
        int parsedMonth;
        int parsedYear;

        if (month == null || year == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
            CustomResponse<String> response = new CustomResponse<>();
            response.setMessage("Month and year are required.");
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        try {
            parsedMonth = month != null ? Integer.parseInt(month) : -1;
            parsedYear = year != null ? Integer.parseInt(year) : -1;
        } catch (NumberFormatException ex) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
            CustomResponse<String> response = new CustomResponse<>();
            response.setMessage("Invalid month or year format.");
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        List<Report> retrievedReports = reportRepository.findByMonthAndYear(parsedMonth, parsedYear);
        
        if (retrievedReports.size() != 0) {
            HttpHeaders headers = new HttpHeaders();
	        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

            String reportString = retrievedReports.get(0).getData();

            CustomResponse<String> response = new CustomResponse<>();
            response.setMessage("Report retrieved.");
            response.setPayload(Collections.singletonList(reportString));
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        }

        String csvDataString = reportManager.generateCsv(parsedMonth, parsedYear);

        reportRepository.save(new Report(parsedMonth, parsedYear, csvDataString));
	   
	    HttpHeaders headers = new HttpHeaders();
	    headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

        CustomResponse<String> response = new CustomResponse<>();
        response.setMessage("Report generated.");
        response.setPayload(Arrays.asList(csvDataString));
	    return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

    @GetMapping("/delete")
    public @ResponseBody ResponseEntity<CustomResponse<Report>> deleteByMonthAndYear(
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String year
    ) {
        int parsedMonth;
        int parsedYear;

        try {
            parsedMonth = month != null ? Integer.parseInt(month) : -1;
            parsedYear = year != null ? Integer.parseInt(year) : -1;
        } catch (NumberFormatException ex) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
            CustomResponse<Report> response = new CustomResponse<>();
            response.setMessage("Invalid month or year format.");
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        List<Report> reports = Arrays.asList();

        if (parsedMonth == -1 && parsedYear == -1) {
            reportRepository.deleteAll();
        } else if (parsedMonth != -1 && parsedYear == -1) {
            reportRepository.deleteByMonth(parsedMonth);
        } else if (parsedYear != -1 && parsedMonth == -1) {
            reportRepository.deleteByYear(parsedYear);
        } else if (parsedMonth != -1 && parsedYear != -1) {
           reportRepository.deleteByMonthAndYear(parsedMonth, parsedYear);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        CustomResponse<Report> response = new CustomResponse<>();
        response.setMessage("Reports deleted.");
        response.setPayload(reports);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    private boolean isValidMonth(String month) {
        return month.length() == 2 && isInteger(month);
    }

    private boolean isValidYear(String year) {
        return year.length() == 4 && isInteger(year);
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
