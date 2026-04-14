package com.project.hospital_bed_monitor.repository;

import com.project.hospital_bed_monitor.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    List<Hospital> findByStatus(String status);
}
