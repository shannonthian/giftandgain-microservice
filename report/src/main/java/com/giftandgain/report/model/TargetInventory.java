package com.giftandgain.report.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "tb_target_inventory")
public class TargetInventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "target_id")
	private Long targetId;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "target_quantity")
	private BigDecimal targetQuantity;

	@Column(name = "target_month_year")
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
