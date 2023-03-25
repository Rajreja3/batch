package com.example.batch.model;

public class Sale {
    private String region;
    private String country;
    private String itemType;
    private String unitPrice;
    private String unitsSold;
    private String totalRevenue;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(String unitsSold) {
        this.unitsSold = unitsSold;
    }

    public String getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(String totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Sale() {
    }

    public Sale(String region, String country, String itemType, String unitPrice, String unitsSold, String totalRevenue) {
        this.region = region;
        this.country = country;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
        this.unitsSold = unitsSold;
        this.totalRevenue = totalRevenue;
    }
}
