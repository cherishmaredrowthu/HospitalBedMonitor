package com.project.hospital_bed_monitor.dto;

public class BedUpdateDTO {
    private Long hospitalId;
    private String bedType;
    private Integer availableBeds;

    // Standard Constructor
    public BedUpdateDTO() {}

    // MANUAL GETTERS AND SETTERS - This ensures Spring can "see" the data
    public Long getHospitalId() { return hospitalId; }
    public void setHospitalId(Long hospitalId) { this.hospitalId = hospitalId; }

    public String getBedType() { return bedType; }
    public void setBedType(String bedType) { this.bedType = bedType; }

    public Integer getAvailableBeds() { return availableBeds; }
    public void setAvailableBeds(Integer availableBeds) { this.availableBeds = availableBeds; }
}