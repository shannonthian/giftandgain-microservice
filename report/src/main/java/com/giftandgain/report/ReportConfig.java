package com.giftandgain.report;

import com.giftandgain.report.model.Report;
import com.giftandgain.report.repository.ReportRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportConfig {

    // This will run when application is started
    // e.g. to seed data
    @Bean
    CommandLineRunner run(ReportRepository reportRepository) {
        return args -> {
            reportRepository.save(new Report("9", "2023", "test_url_1"));
            reportRepository.save(new Report("10", "2023", "test_url_2"));

        };
    }
}
