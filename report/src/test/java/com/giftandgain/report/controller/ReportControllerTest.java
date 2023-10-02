package com.giftandgain.report.controller;

import com.giftandgain.report.model.Report;
import com.giftandgain.report.repository.ReportRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportRepository reportRepository;


    @BeforeEach
    public void setup() {
    }

    @Test
    public void shouldRetrieveReportByMonthAndYear() throws Exception {
        // Arrange
        Report report = new Report();
        report.setMonth("09");
        report.setYear("2023");
        reportRepository.save(report);

        // Act and Assert
        mockMvc.perform(get("/api/report")
                        .param("month", "09")
                        .param("year", "2023"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/report")
                        .param("month", "Sep")
                        .param("year", "2023"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/report")
                        .param("month", "09")
                        .param("year", "abc"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/report"))
                .andExpect(status().isOk());
    }


    @AfterEach
    public void tearDown() {
        // Clean up resources or reset any changes made during tests
        reportRepository.deleteAll();
    }
}