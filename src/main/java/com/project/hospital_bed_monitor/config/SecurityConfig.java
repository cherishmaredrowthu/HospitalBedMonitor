package com.project.hospital_bed_monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Required for plain-text password testing
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. PUBLIC PATHS
                        .requestMatchers("/", "/index.html", "/login.html", "/hospitalregistration.html", "/api/public/**", "/css/**", "/js/**").permitAll()

                        // 2. ADMIN PATHS
                        .requestMatchers("/admin.html", "/api/admin/**").hasRole("ADMIN")

                        // 3. STAFF PATHS
                        .requestMatchers("/staff.html", "/api/staff/**").hasRole("STAFF")

                        // 4. ALL OTHERS
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html") // Use our custom beautiful login page
                        .loginProcessingUrl("/login") // The URL the form posts to
                        .successHandler((request, response, authentication) -> {
                            // CUSTOM REDIRECT LOGIC:
                            // If Admin logs in -> go to admin.html
                            // If Staff logs in -> go to staff.html
                            var authorities = authentication.getAuthorities();
                            String redirectUrl = "/index.html";

                            for (var auth : authorities) {
                                if (auth.getAuthority().equals("ROLE_ADMIN")) {
                                    redirectUrl = "/admin.html";
                                    break;
                                } else if (auth.getAuthority().equals("ROLE_STAFF")) {
                                    redirectUrl = "/staff.html";
                                    break;
                                }
                            }
                            response.sendRedirect(redirectUrl);
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/index.html")
                        .permitAll()
                );

        return http.build();
    }
}