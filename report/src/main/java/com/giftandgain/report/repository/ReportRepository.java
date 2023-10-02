package com.giftandgain.report.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.giftandgain.report.model.Report;

public interface ReportRepository extends MongoRepository<Report, String> {
    List<Report> findByMonthAndYear(String month, String year);
    List<Report> findByMonth(String month);
    List<Report> findByYear(String year);
}
