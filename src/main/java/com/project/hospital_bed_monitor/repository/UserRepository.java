package com.project.hospital_bed_monitor.repository;

import com.project.hospital_bed_monitor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom method to find user by username for login
    Optional<User> findByUsername(String username);
}
