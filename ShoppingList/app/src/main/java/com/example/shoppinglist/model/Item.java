package com.example.shoppinglist.model;

public class Item {

    private int id;
    private String itemName;
    private String itemBrand;
    private String itemQuality;
    private String itemQuantity;
    private String itemAdded;

    public Item() {
    }

    public Item(String itemName, String itemBrand, String itemQuality, String itemQuantity, String itemAdded) {
        this.itemName = itemName;
        this.itemBrand = itemBrand;
        this.itemQuality = itemQuality;
        this.itemQuantity = itemQuantity;
        this.itemAdded = itemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getItemQuality() {
        return itemQuality;
    }

    public void setItemQuality(String itemQuality) {
        this.itemQuality = itemQuality;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemAdded() {
        return itemAdded;
    }

    public void setItemAdded(String itemAdded) {
        this.itemAdded = itemAdded;
    }
}
