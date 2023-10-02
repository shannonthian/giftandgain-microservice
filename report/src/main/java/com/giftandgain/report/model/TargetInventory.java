package com.giftandgain.report.model;

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
	private int targetQuantity;

	@Column(name = "target_month")
	private String targetMonth;

	@Column(name = "target_year")
	private String targetYear;

	public TargetInventory() {

	}

	@Override
	public String toString() {
		return "TargetInventory [targetId=" + targetId + ", category=" + category + ", unit=" + unit + ", targetQuantity="
				+ targetQuantity + ", targetMonth=" + targetMonth + ", targetYear=" + targetYear + "]";
	}

	public TargetInventory(Long targetId, String category, String unit, int targetQuantity, String targetMonth,
			String targetYear) {
		super();
		this.targetId = targetId;
		this.category = category;
		this.unit = unit;
		this.targetQuantity = targetQuantity;
		this.targetMonth = targetMonth;
		this.targetYear = targetYear;
	}

	public TargetInventory(String category, String unit, int targetQuantity, String targetMonth,
			String targetYear) {
		super();
		this.category = category;
		this.unit = unit;
		this.targetQuantity = targetQuantity;
		this.targetMonth = targetMonth;
		this.targetYear = targetYear;
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

	public int getTargetQuantity() {
		return targetQuantity;
	}

	public void setTargetQuantity(int targetQuantity) {
		this.targetQuantity = targetQuantity;
	}

	public String getTargetMonth() {
		return targetMonth;
	}

	public void setTargetMonth(String targetMonth) {
		this.targetMonth = targetMonth;
	}

	public String getTargetYear() {
		return targetYear;
	}

	public void setTargetYear(String targetYear) {
		this.targetYear = targetYear;
	}

}
