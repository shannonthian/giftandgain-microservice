package com.giftandgain.inventorymanagement;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tb_inventory")
public class InventoryManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "type_of_donation")
	private String typeOfDonation;

	@Column(name = "expiry_date")
	private LocalDate expiryDate;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	public InventoryManagement() {

	}

	@Override
	public String toString() {
		return "InventoryManagement [id=" + id + ", itemName=" + itemName + ", quantity=" + quantity
				+ ", typeOfDonation=" + typeOfDonation + ", expiryDate=" + expiryDate + ", createdDate=" + createdDate
				+ "]";
	}

	public InventoryManagement(Long id, String itemName, int quantity, String typeOfDonation, LocalDate expiryDate,
			LocalDateTime createdDate) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.quantity = quantity;
		this.typeOfDonation = typeOfDonation;
		this.expiryDate = expiryDate;
		this.createdDate = createdDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTypeOfDonation() {
		return typeOfDonation;
	}

	public void setTypeOfDonation(String typeOfDonation) {
		this.typeOfDonation = typeOfDonation;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDateToNow() {
	    this.createdDate = LocalDateTime.now();
	}

}
