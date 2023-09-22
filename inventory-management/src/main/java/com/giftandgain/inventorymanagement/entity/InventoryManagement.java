package com.giftandgain.inventorymanagement.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "tb_inventory")
public class InventoryManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventory_id")
	private Long inventoryId;

	@Column(name = "item_name")
	private String itemName;

	@ManyToOne
    @JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
    @JoinColumn(name = "unit_id")
	private Unit unit;

	@Column(name = "received_quantity")
	private BigDecimal receivedQuantity;

	@Column(name = "expiry_date")
	private LocalDate expiryDate;

	@Column(name = "created_date")
	private LocalDate createdDate;

	@Column(name = "remarks")
	private String remarks;

	public InventoryManagement() {

	}

	@Override
	public String toString() {
		return "InventoryManagement [inventoryId=" + inventoryId + ", itemName=" + itemName + ", category=" + category + ", unit=" + unit
				+ ", receivedQuantity=" + receivedQuantity + ", expiryDate=" + expiryDate + ", createdDate="
				+ createdDate + ", remarks=" + remarks + "]";
	}

	public InventoryManagement(Long InventoryId, String itemName, Category category, Unit unit, BigDecimal receivedQuantity,
			LocalDate expiryDate, LocalDate createdDate, String remarks) {
		super();
		this.inventoryId = InventoryId;
		this.itemName = itemName;
		this.category = category;
		this.unit = unit;
		this.receivedQuantity = receivedQuantity;
		this.expiryDate = expiryDate;
		this.createdDate = createdDate;
		this.remarks = remarks;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public BigDecimal getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(BigDecimal receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDateToNow() {
	    this.createdDate = LocalDate.now();
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
