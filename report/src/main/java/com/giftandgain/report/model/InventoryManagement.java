package com.giftandgain.report.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

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

	@Column(name = "received_quantity")
	private BigDecimal receivedQuantity;

	@Column(name = "expiry_date")
	private LocalDate expiryDate;

	@Column(name = "created_date")
	private LocalDate createdDate;

	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "created_by")
	private String createdBy;

	public InventoryManagement() {

	}

	@Override
	public String toString() {
		return "InventoryManagement [inventoryId=" + inventoryId + ", itemName=" + itemName + ", category=" + category + ",  receivedQuantity=" + receivedQuantity + ", expiryDate=" + expiryDate + ", createdDate="
				+ createdDate + ", remarks=" + remarks + ", createdBy=" + createdBy +",]";
	}

	public InventoryManagement(Long InventoryId, String itemName, Category category, BigDecimal receivedQuantity,
			LocalDate expiryDate, LocalDate createdDate, String remarks, String createdBy) {
		super();
		this.inventoryId = InventoryId;
		this.itemName = itemName;
		this.category = category;
		this.receivedQuantity = receivedQuantity;
		this.expiryDate = expiryDate;
		this.createdDate = createdDate;
		this.remarks = remarks;
		this.createdBy = createdBy;
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
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}