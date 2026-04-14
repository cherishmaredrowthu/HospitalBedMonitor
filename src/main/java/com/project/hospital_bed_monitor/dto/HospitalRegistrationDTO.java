package com.project.hospital_bed_monitor.dto;

import lombok.Data;

public class HospitalRegistrationDTO {
    private String name;
    private String address;
    private String phoneNumber;
    private String websiteUrl;
    private Double latitude;
    private Double longitude;

    // Bed Counts
    private Integer icuTotal;
    private Integer oxygenTotal;
    private Integer generalTotal;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getWebsiteUrl() { return websiteUrl; }
    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Integer getIcuTotal() { return icuTotal; }
    public void setIcuTotal(Integer icuTotal) { this.icuTotal = icuTotal; }
    public Integer getOxygenTotal() { return oxygenTotal; }
    public void setOxygenTotal(Integer oxygenTotal) { this.oxygenTotal = oxygenTotal; }
    public Integer getGeneralTotal() { return generalTotal; }
    public void setGeneralTotal(Integer generalTotal) { this.generalTotal = generalTotal; }
}
