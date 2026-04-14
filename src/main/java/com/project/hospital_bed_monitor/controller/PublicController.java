package com.project.hospital_bed_monitor.controller;

import com.project.hospital_bed_monitor.dto.HospitalRegistrationDTO;
import com.project.hospital_bed_monitor.entity.BedAvailability;
import com.project.hospital_bed_monitor.entity.Hospital;
import com.project.hospital_bed_monitor.repository.BedRepository;
import com.project.hospital_bed_monitor.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
public class PublicController {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private BedRepository bedRepository;

    /**
     * Retrieves all hospitals that are marked as ACTIVE.
     * Used by the main map (index.html).
     */
    @GetMapping("/hospitals")
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findByStatus("ACTIVE");
    }

    /**
     * Handles the public registration of a new hospital.
     * Sets initial bed counts provided by the hospital during sign-up.
     */
    @PostMapping("/register-hospital")
    public ResponseEntity<?> registerHospital(@RequestBody HospitalRegistrationDTO dto) {
        try {
            // 1. Map DTO to Hospital Entity
            Hospital hospital = new Hospital();
            hospital.setName(dto.getName());
            hospital.setAddress(dto.getAddress());
            hospital.setPhoneNumber(dto.getPhoneNumber());
            hospital.setWebsiteUrl(dto.getWebsiteUrl());
            hospital.setLatitude(dto.getLatitude());
            hospital.setLongitude(dto.getLongitude());
            hospital.setStatus("PENDING"); // Admin must approve before it appears on map

            // 2. Save the hospital to the database
            Hospital savedHospital = hospitalRepository.save(hospital);

            // 3. Create initial bed records based on the user's input in the form
            createInitialBedRecord(savedHospital, "ICU", dto.getIcuTotal());
            createInitialBedRecord(savedHospital, "OXYGEN", dto.getOxygenTotal());
            createInitialBedRecord(savedHospital, "GENERAL", dto.getGeneralTotal());

            return ResponseEntity.ok("Application submitted successfully! Your hospital is currently PENDING. Admin will verify your details soon.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error submitting registration: " + e.getMessage());
        }
    }

    /**
     * Helper method to create bed records for a new hospital.
     * Initially, available beds are set equal to total beds.
     */
    private void createInitialBedRecord(Hospital hospital, String type, Integer total) {
        BedAvailability bed = new BedAvailability();
        bed.setHospital(hospital);
        bed.setBedType(type);
        bed.setTotalBeds(total != null ? total : 0);
        bed.setAvailableBeds(total != null ? total : 0); // Initially, all beds are free
        bed.setLastUpdated(LocalDateTime.now());
        bedRepository.save(bed);
    }
}