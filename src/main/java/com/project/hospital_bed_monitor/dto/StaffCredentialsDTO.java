package com.project.hospital_bed_monitor.dto;

public class StaffCredentialsDTO {
    private String username;
    private String password;

    public StaffCredentialsDTO(String u, String p) {
        this.username = u;
        this.password = p;
    }
    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
