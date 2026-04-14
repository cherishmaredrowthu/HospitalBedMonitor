package com.project.hospital_bed_monitor.repository;

import com.project.hospital_bed_monitor.entity.BedAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BedRepository extends JpaRepository<BedAvailability, Long> {

    // This query is now "Bulletproof": It trims spaces and ignores case
    @Query("SELECT b FROM BedAvailability b WHERE b.hospital.id = :hId " +
            "AND UPPER(TRIM(b.bedType)) = UPPER(TRIM(:bType))")
    BedAvailability findBed(@Param("hId") Long hId, @Param("bType") String bType);
}