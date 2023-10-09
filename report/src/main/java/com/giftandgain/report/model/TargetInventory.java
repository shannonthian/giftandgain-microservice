package com.giftandgain.report.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TargetInventory {


    private Long targetId;


    private Category category;


    private BigDecimal targetQuantity;


    private LocalDate targetMonthYear;

    public TargetInventory() {
    }

    public TargetInventory(Long targetId, Category category, BigDecimal targetQuantity, LocalDate targetMonthYear) {
        this.targetId = targetId;
        this.category = category;
        this.targetQuantity = targetQuantity;
        this.targetMonthYear = targetMonthYear;
    }

    @Override
    public String toString() {
        return "TargetInventory [targetId=" + targetId + ", category=" + category + ", targetQuantity=" + targetQuantity + ", targetMonthYear=" + targetMonthYear + "]";
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getTargetQuantity() {
        return targetQuantity;
    }

    public void setTargetQuantity(BigDecimal targetQuantity) {
        this.targetQuantity = targetQuantity;
    }

    public LocalDate getTargetMonthYear() {
        return targetMonthYear;
    }

    public void setTargetMonthYear(LocalDate targetMonthYear) {
        this.targetMonthYear = targetMonthYear;
    }
}
