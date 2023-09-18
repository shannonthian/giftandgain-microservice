package com.giftandgain.inventorymanagement.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tb_inventory")
public class InventoryManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventory_id")
	private Long inventoryId;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "category")
	private String category;

	@Column(name = "unit")
	private String unit;

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

	public InventoryManagement(Long InventoryId, String itemName, String category, String unit, BigDecimal receivedQuantity,
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
