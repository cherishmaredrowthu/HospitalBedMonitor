🏥 BedMonitor Live: Real-Time Hospital Bed Availability & Navigation

BedMonitor Live is a full-stack web application designed to bridge the gap between patients and emergency medical resources. It enables real-time tracking of hospital bed availability (ICU, Oxygen-supported, and General beds) along with GPS-based proximity search and live navigation.

🚀 Key Features
🌍 Public Interface
Geo-Aware Search: Detects user location and sorts hospitals by proximity using the Haversine formula
Live Navigation: Turn-by-turn navigation using Leaflet Routing Machine
Data Freshness: Displays Last Updated timestamps for reliability
Smart Filtering: Filter hospitals by bed type and availability
🔐 Hospital Staff Portal
Real-Time Updates: Staff can instantly update bed availability
Secure Login Flow: Forced password reset on first login
Auto Sync: Updates reflect on public dashboard without reload
🛡️ Admin Dashboard
Verification Workflow: Admin validates hospital details before activation
System Analytics: Overview of total bed capacity and usage
Critical Alerts: Red-zone alerts when bed availability reaches zero
🛠️ Technology Stack
Backend: Java Spring Boot, Spring Security, Spring Data JPA
Database: MySQL / TiDB Cloud
Frontend: HTML5, CSS3 (Bootstrap 5), JavaScript (ES6+)
Mapping: Leaflet.js, OpenStreetMap
Routing: Leaflet Routing Machine, Nominatim Geocoder
📂 Project Structure
src/main/java/com/project/hospital_bed_monitor/
├── config/        # Security & API configurations
├── controller/    # REST Controllers (Admin, Staff, Public)
├── dto/           # Data Transfer Objects
├── entity/        # Database Entities
├── repository/    # JPA Repositories
└── service/       # Business Logic

src/main/resources/
├── static/        # Frontend (HTML, CSS, JS)
└── application.properties
⚙️ Setup & Installation
1. Prerequisites
Java 17+
MySQL 8+
Maven
2. Database Setup
CREATE DATABASE hospital_db;

Tables will be created automatically on first run (spring.jpa.hibernate.ddl-auto=update)

3. Configuration

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=your_username
spring.datasource.password=your_password
4. Run Application
mvn clean install
mvn spring-boot:run

Access the app at:
👉 http://localhost:8080

👨‍🏫 Evaluation Highlights
Haversine Formula: Custom implementation for accurate distance calculation
RBAC (Role-Based Access Control): Separate roles for Public, Staff, Admin
Traffic Buffer Logic: 1.3x multiplier for realistic travel time estimates
Optimized Relationships: Efficient @OneToMany mapping with recursion handling
📌 Future Enhancements
Mobile application (Android/iOS)
Real-time notifications for nearby hospitals
AI-based demand prediction for hospital resources
Donor/volunteer integration for emergency support
🤝 Contributing

Contributions are welcome! Feel free to fork the repo and submit a pull request.
