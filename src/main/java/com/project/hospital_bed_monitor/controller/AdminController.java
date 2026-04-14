package com.project.hospital_bed_monitor.controller;

import com.project.hospital_bed_monitor.dto.StaffCredentialsDTO;
import com.project.hospital_bed_monitor.entity.BedAvailability;
import com.project.hospital_bed_monitor.entity.Hospital;
import com.project.hospital_bed_monitor.entity.User;
import com.project.hospital_bed_monitor.repository.HospitalRepository;
import com.project.hospital_bed_monitor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 1. View all hospitals.
     */
    @GetMapping("/hospitals")
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    /**
     * 2. System Analytics Dashboard Logic.
     * Calculates total hospitals, pending requests, and total bed capacity across the system.
     */
    @GetMapping("/analytics")
    public ResponseEntity<?> getSystemAnalytics() {
        List<Hospital> allHospitals = hospitalRepository.findAll();

        long totalHospitals = allHospitals.size();
        long activeHospitals = allHospitals.stream().filter(h -> "ACTIVE".equalsIgnoreCase(h.getStatus())).count();
        long pendingHospitals = allHospitals.stream().filter(h -> "PENDING".equalsIgnoreCase(h.getStatus())).count();

        // Sum up beds from all ACTIVE hospitals
        int totalIcu = 0, availIcu = 0;
        int totalOxy = 0, availOxy = 0;
        int totalGen = 0, availGen = 0;

        for (Hospital h : allHospitals) {
            if ("ACTIVE".equalsIgnoreCase(h.getStatus()) && h.getBeds() != null) {
                for (BedAvailability b : h.getBeds()) {
                    if ("ICU".equalsIgnoreCase(b.getBedType())) {
                        totalIcu += b.getTotalBeds();
                        availIcu += b.getAvailableBeds();
                    } else if ("OXYGEN".equalsIgnoreCase(b.getBedType())) {
                        totalOxy += b.getTotalBeds();
                        availOxy += b.getAvailableBeds();
                    } else if ("GENERAL".equalsIgnoreCase(b.getBedType())) {
                        totalGen += b.getTotalBeds();
                        availGen += b.getAvailableBeds();
                    }
                }
            }
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalHospitals", totalHospitals);
        stats.put("activeHospitals", activeHospitals);
        stats.put("pendingHospitals", pendingHospitals);

        stats.put("totalIcu", totalIcu);
        stats.put("availIcu", availIcu);
        stats.put("totalOxy", totalOxy);
        stats.put("availOxy", availOxy);
        stats.put("totalGen", totalGen);
        stats.put("availGen", availGen);

        return ResponseEntity.ok(stats);
    }

    /**
     * 3. Toggle Hospital Status (ACTIVE <-> INACTIVE).
     */
    @PutMapping("/hospitals/{id}/toggle-status")
    public ResponseEntity<?> toggleHospitalStatus(@PathVariable Long id) {
        return hospitalRepository.findById(id).map(hospital -> {
            String currentStatus = hospital.getStatus();

            if ("ACTIVE".equalsIgnoreCase(currentStatus)) {
                hospital.setStatus("INACTIVE");
                hospitalRepository.save(hospital);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Hospital deactivated successfully");
                return ResponseEntity.ok(response);
            } else {
                hospital.setStatus("ACTIVE");
                hospitalRepository.save(hospital);

                boolean userExists = userRepository.findAll().stream()
                        .anyMatch(u -> id.equals(u.getHospitalId()));

                if (!userExists) {
                    String cleanName = hospital.getName().toLowerCase().replaceAll("\\s+", "");
                    String generatedUsername = "staff_" + cleanName + (int)(Math.random() * 90 + 10);
                    String generatedPassword = "Pwd" + (int)(Math.random() * 9000 + 1000);

                    User newStaff = new User();
                    newStaff.setUsername(generatedUsername);
                    newStaff.setPassword(generatedPassword);
                    newStaff.setRole("ROLE_STAFF");
                    newStaff.setHospitalId(hospital.getId());
                    userRepository.save(newStaff);

                    return ResponseEntity.ok(new StaffCredentialsDTO(generatedUsername, generatedPassword));
                }

                Map<String, String> response = new HashMap<>();
                response.put("message", "Hospital reactivated successfully");
                return ResponseEntity.ok(response);
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * 4. Delete a hospital record.
     */
    @DeleteMapping("/hospitals/{id}")
    public ResponseEntity<?> deleteHospital(@PathVariable Long id) {
        if (hospitalRepository.existsById(id)) {
            hospitalRepository.deleteById(id);
            return ResponseEntity.ok("Hospital record deleted successfully!");
        }
        return ResponseEntity.notFound().build();
    }
}