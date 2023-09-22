package com.giftandgain.report.controller;

import com.giftandgain.report.model.Report;
import com.giftandgain.report.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/report")
public class ReportController {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<Report>> findByMonthAndYear(@RequestParam(defaultValue = "9") String month, @RequestParam(defaultValue = "2023") String year) {
        List<Report> reports = reportRepository.findByMonthAndYear(month, year);

        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
}
