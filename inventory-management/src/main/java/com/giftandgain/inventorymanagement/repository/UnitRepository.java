package com.giftandgain.inventorymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.giftandgain.inventorymanagement.entity.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {

    // This method will check if a unit with the given unit name exists
    boolean existsByUnit(String unitName);
    
    boolean existsByUnitAndUnitIdNot(String unit, Long unitId);
}
