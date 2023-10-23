package com.giftandgain.report.model;

import java.math.BigDecimal;

public class InventoryReportDTO {
    private String category;
    private String unit;
    private BigDecimal targetQuantity;
    private BigDecimal receivedQuantity;

    public InventoryReportDTO(String category, String unit, BigDecimal targetQuantity, BigDecimal receivedQuantity) {
        this.category = category;
        this.unit = unit;
        this.targetQuantity = targetQuantity;
        this.receivedQuantity = receivedQuantity;
    }

    // Getters and setters

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getTargetQuantity() {
        return targetQuantity;
    }

    public void setTargetQuantity(BigDecimal targetQuantity) {
        this.targetQuantity = targetQuantity;
    }

    public BigDecimal getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(BigDecimal receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }
}
