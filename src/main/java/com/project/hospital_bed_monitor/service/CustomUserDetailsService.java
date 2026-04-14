package com.project.hospital_bed_monitor.service;

import com.project.hospital_bed_monitor.entity.User;
import com.project.hospital_bed_monitor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("--- Login Attempt for: " + username + " ---");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Step 1: Fix the role string (e.g., "STAFF" -> "ROLE_STAFF")
        String roleWithPrefix = user.getRole().toUpperCase().startsWith("ROLE_") ?
                user.getRole().toUpperCase() : "ROLE_" + user.getRole().toUpperCase();

        System.out.println("Final Authority being assigned: " + roleWithPrefix);

        // Step 2: Use roleWithPrefix in the authorities() method
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(roleWithPrefix)
                .build();
    }
}