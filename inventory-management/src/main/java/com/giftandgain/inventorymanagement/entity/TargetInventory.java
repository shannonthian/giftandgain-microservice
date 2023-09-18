package com.giftandgain.inventorymanagement.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tb_target_inventory")
public class TargetInventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "target_id")
	private Long targetId;

	@Column(name = "category")
	private String category;

	@Column(name = "unit")
	private String unit;

	@Column(name = "target_quantity")
	private BigDecimal targetQuantity;

	@Column(name = "target_month_year")
	private LocalDate targetMonthYear;

	public TargetInventory() {

	}

	@Override
	public String toString() {
		return "TargetInventory [targetId=" + targetId + ", category=" + category + ", unit=" + unit
				+ ", targetQuantity=" + targetQuantity + ", targetMonthYear=" + targetMonthYear + "]";
	}

	public TargetInventory(Long targetId, String category, String unit, BigDecimal targetQuantity,
			LocalDate targetMonthYear) {
		super();
		this.targetId = targetId;
		this.category = category;
		this.unit = unit;
		this.targetQuantity = targetQuantity;
		this.targetMonthYear = targetMonthYear;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
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
