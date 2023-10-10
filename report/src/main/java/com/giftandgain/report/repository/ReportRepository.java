package com.giftandgain.report.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.giftandgain.report.model.Report;

public interface ReportRepository extends MongoRepository<Report, String> {
    List<Report> findByMonthAndYear(int month, int year);
    List<Report> findByMonth(int month);
    List<Report> findByYear(int year);
}
