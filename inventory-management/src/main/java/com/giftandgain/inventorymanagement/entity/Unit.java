package com.giftandgain.inventorymanagement.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "tb_unit")
public class Unit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "unit_id")
	private Long unitId;

	@Column(name = "unit")
	private String unit;

	@Column(name = "description")
	private String description;
	
	// Relationships
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private List<TargetInventory> targetInventories;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private List<InventoryManagement> inventories;

	public Unit() {

	}

	@Override
	public String toString() {
		return "Unit [unitId=" + unitId + ", unit=" + unit + ", description=" + description + "]";
	}

	public Unit(Long unitId, String unit, String description) {
		super();
		this.unitId = unitId;
		this.unit = unit;
		this.description = description;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
