package com.eng.chula.se.hygeia.models;

public class MapDrugStoreResponse {
    private String storeId;
    private String storeName;
    private String ownerName;
    private String licenseId;
    private String storeLocation;
    private String isQualified;

    public MapDrugStoreResponse(String storeId, String storeName, String ownerName,
                                String licenseId, String storeLocation, String isQualified) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.ownerName = ownerName;
        this.licenseId = licenseId;
        this.storeLocation = storeLocation;
        this.isQualified = isQualified;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public String getIsQualified() {
        return isQualified;
    }
}
