package com.giftandgain.report.model;

import java.util.List;

public class Category {

    private Long categoryId;

    private String category;

    private String unit;

    private String status;


    private List<TargetInventory> targetInventories;

    private List<InventoryManagement> inventories;

    public Category() {

    }

    @Override
    public String toString() {
        return "Category [categoryId=" + categoryId + ", category=" + category + ", unit=" + unit + ", status=" + status
                + ", targetInventories=" + targetInventories + ", inventories=" + inventories + "]";
    }

    public Category(Long categoryId, String category, String unit, String status) {
        super();
        this.categoryId = categoryId;
        this.category = category;
        this.unit = unit;
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

