package com.giftandgain.listing.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="listing", schema="public")
public class Listing implements Serializable {
    @Id
    @SequenceGenerator(
            name="listing_sequence",
            sequenceName="listing_sequence",
            allocationSize=1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "listing_sequence"
    )
    @JsonProperty("id")
    private Long id;
    @JsonProperty("itemName")
    private String itemName;
    @JsonProperty("quantity")
    private int quantity;

    public Listing() {
    }

    public Listing(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
