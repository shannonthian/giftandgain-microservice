package com.giftandgain.report.model;

import javax.persistence.*;
import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("report")
public class Report implements Serializable {
    // Omit the @Id annotation and id field entirely
    private String month;
    private String year;
    private String data;

    public Report() {
        // Default constructor for JPA
    }

    public Report(String month, String year, String data) {
        this.month = month;
        this.year = year;
        this.data = data;
    }

    // Getters and setters (if needed)

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getData() {
        return data;
    }

    public void setData(String url) {
        this.data = url;
    }

    @Override
    public String toString() {
        return "Report{" +
                // Remove id from the toString representation
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", url='" + data + '\'' +
                '}';
    }
}
