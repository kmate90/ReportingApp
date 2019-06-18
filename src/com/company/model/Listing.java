package com.company.model;

public class Listing {

    private String id;
    private String title;
    private String description;
    private String inventoryItemLocationId;
    private float listingPrice;
    private String currency;
    private int quantity;
    private int listingStatus;
    private int marketplace;
    private String uploadTime;
    private String ownerEmailAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInventoryItemLocationId() {
        return inventoryItemLocationId;
    }

    public void setInventoryItemLocationId(String inventoryItemLocationId) {
        this.inventoryItemLocationId = inventoryItemLocationId;
    }

    public float getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(float listingPrice) {
        this.listingPrice = listingPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getListingStatus() {
        return listingStatus;
    }

    public String getListingStatusToString(){
        return String.valueOf(listingStatus);
    }

    public void setListingStatus(int listingStatus) {
        this.listingStatus = listingStatus;
    }

    public int getMarketplace() {
        return marketplace;
    }
    public String getMarketplaceToString() {
        return String.valueOf(marketplace);
    }

    public void setMarketplace(int marketplace) {
        this.marketplace = marketplace;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getOwnerEmailAddress() {
        return ownerEmailAddress;
    }

    public void setOwnerEmailAddress(String ownerEmailAddress) {
        this.ownerEmailAddress = ownerEmailAddress;
    }
}
