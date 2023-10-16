package com.giftandgain.report;

import com.giftandgain.report.controller.ReportController;
import com.giftandgain.report.manager.ReportManager;
import com.giftandgain.report.model.Report;
import com.giftandgain.report.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportRepository reportRepository;

    @MockBean
    private ReportManager reportManager;

    @Autowired
    private ReportController reportController;

    private String apiReport = "/api/report";

    private String apiReportDownload = "/api/report/download";

    @Test
    public void testFindByMonthAndYear_ValidInput() throws Exception {
        int parsedMonth = 1;
        int parsedYear = 2023;
        List<Report> mockReports = Arrays.asList(new Report());

        Mockito.when(reportRepository.findByMonthAndYear(parsedMonth, parsedYear))
                .thenReturn(mockReports);

        mockMvc.perform(MockMvcRequestBuilders.get(apiReport)
                        .param("month", "1")
                        .param("year", "2023")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testFindByMonthAndYear_InvalidInput() throws Exception {
        // Define the behavior of your mock objects
        Mockito.when(reportRepository.findByMonthAndYear(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Arrays.asList());

        mockMvc.perform(MockMvcRequestBuilders.get(apiReport)
                        .param("month", "invalid")
                        .param("year", "2023")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testDownloadReport_ValidInput() throws Exception {
        int parsedMonth = 1;
        int parsedYear = 2023;
       String mockData = "mock data";

        Mockito.when(reportRepository.findByMonthAndYear(parsedMonth, parsedYear))
                .thenReturn(Arrays.asList(new Report(parsedMonth, parsedYear, mockData)));

        mockMvc.perform(MockMvcRequestBuilders.get(apiReportDownload)
                        .param("month", "1")
                        .param("year", "2023")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testDownloadReport_IfReportNotFoundThenGenerate() throws Exception {
        int parsedMonth = 1;
        int parsedYear = 2023;

        Mockito.when(reportRepository.findByMonthAndYear(parsedMonth, parsedYear))
                .thenReturn(Arrays.asList());

        String mockData = "mock data";
        Mockito.when(reportManager.generateCsv(parsedMonth, parsedYear))
                .thenReturn(mockData);

        mockMvc.perform(MockMvcRequestBuilders.get(apiReportDownload)
                        .param("month", "1")
                        .param("year", "2023")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
