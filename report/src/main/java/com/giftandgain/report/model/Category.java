package com.giftandgain.report.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category")
    private String category;

    @Column(name = "unit")
    private String unit;

    @Column(name = "status")
    private String status;

    // Relationships
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<TargetInventory> targetInventories;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
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


