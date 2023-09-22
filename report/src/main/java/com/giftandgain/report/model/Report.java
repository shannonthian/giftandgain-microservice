package com.giftandgain.report.model;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String month;
    private String year;
    private String url;

    public Report() {
        // Default constructor for JPA
    }

    public Report(String month, String year, String url) {
        this.month = month;
        this.year = year;
        this.url = url;
    }

    // Getters and setters (if needed)

    public Long getId() {
        return id;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
