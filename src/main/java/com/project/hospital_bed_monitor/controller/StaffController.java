package com.project.hospital_bed_monitor.controller;

import com.project.hospital_bed_monitor.entity.BedAvailability;
import com.project.hospital_bed_monitor.entity.Hospital;
import com.project.hospital_bed_monitor.entity.User;
import com.project.hospital_bed_monitor.repository.BedRepository;
import com.project.hospital_bed_monitor.repository.HospitalRepository;
import com.project.hospital_bed_monitor.repository.UserRepository;
import com.project.hospital_bed_monitor.dto.BedUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * GET: Returns details of a specific hospital.
     */
    @GetMapping("/hospital/{id}")
    public ResponseEntity<?> getHospitalDetails(@PathVariable Long id) {
        System.out.println("LOG: Staff Page loading Hospital ID: " + id);
        return hospitalRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST: Updates bed counts.
     */
    @PostMapping("/update-beds")
    public ResponseEntity<?> updateBedAvailability(@RequestBody BedUpdateDTO updateData) {

        if (updateData.getHospitalId() == null || updateData.getHospitalId() == 0) {
            System.out.println("LOG ERROR: Received null or invalid Hospital ID");
            return ResponseEntity.badRequest().body("Error: Hospital ID is missing.");
        }

        String cleanType = updateData.getBedType().trim().toUpperCase();
        Long hId = updateData.getHospitalId();

        System.out.println("LOG: Update Request -> Hospital: " + hId + " | Type: " + cleanType);

        BedAvailability bed = bedRepository.findBed(hId, cleanType);

        if (bed == null) {
            System.out.println("LOG ERROR: No bed record found for " + cleanType + " at Hospital " + hId);
            return ResponseEntity.badRequest().body("Error: Bed type '" + cleanType + "' not found.");
        }

        bed.setAvailableBeds(updateData.getAvailableBeds());
        bed.setLastUpdated(LocalDateTime.now());
        bedRepository.save(bed);

        System.out.println("LOG SUCCESS: Updated " + cleanType + " to " + updateData.getAvailableBeds());
        return ResponseEntity.ok("Successfully updated " + cleanType + " beds!");
    }

    /**
     * GET: Finds the hospital linked to the logged-in staff member.
     * Updated: Returns a map containing hospital info and the firstLogin flag.
     */
    @GetMapping("/my-hospital")
    public ResponseEntity<?> getMyHospital(java.security.Principal principal) {
        if (principal == null) return ResponseEntity.status(401).build();

        return userRepository.findByUsername(principal.getName())
                .map(user -> {
                    if (user.getHospitalId() == null) return ResponseEntity.status(404).body("No hospital assigned.");

                    Hospital hospital = hospitalRepository.findById(user.getHospitalId()).orElse(null);

                    // Construct a custom response including the firstLogin flag
                    Map<String, Object> response = new HashMap<>();
                    response.put("hospital", hospital);
                    response.put("firstLogin", user.isFirstLogin());

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST: Handles password change for staff.
     * Sets firstLogin to false after successful update.
     */
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request, java.security.Principal principal) {
        if (principal == null) return ResponseEntity.status(401).build();

        String newPassword = request.get("newPassword");
        if (newPassword == null || newPassword.length() < 4) {
            return ResponseEntity.badRequest().body("Password must be at least 4 characters long.");
        }

        return userRepository.findByUsername(principal.getName())
                .map(user -> {
                    user.setPassword(newPassword);
                    user.setFirstLogin(false); // Mark first login as completed
                    userRepository.save(user);
                    System.out.println("LOG: Password updated for user: " + principal.getName());
                    return ResponseEntity.ok("Password updated successfully.");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}