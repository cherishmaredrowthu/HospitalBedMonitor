🏥 BedMonitor Live: Real-Time Hospital Bed Availability & Navigation
BedMonitor Live is a full-stack web application designed to bridge the gap between patients and emergency medical resources. The system provides real-time tracking of critical hospital resources like ICU, Oxygen-supported, and General beds, combined with GPS-based proximity sorting and live navigation.
🚀 Key Features
🌍 Public Interface
Geo-Aware Search: Automatically detects user location and sorts hospitals by proximity using the Haversine formula.
Live Navigation: Integrated Leaflet Routing Machine providing turn-by-turn directions with traffic-adjusted travel time estimates.
Data Freshness: Displays "Last Updated" timestamps for every hospital to ensure data reliability during emergencies.
Smart Filtering: Filter hospitals by bed type (ICU, Oxygen, General) and availability.
🔐 Hospital Staff Portal
Secure Synchronization: Real-time dashboard for staff to update available bed counts instantly.
Security Lifecycle: Forced password change policy upon first-time login with temporary credentials.
Automatic Status Sync: Updates made by staff are reflected on the public map immediately without page reloads.
🛡️ Admin Dashboard
Verification Workflow: Gatekeeper logic where Admins verify a hospital's phone and website before activation.
System Analytics: High-level overview of total bed capacity and usage across the entire network.
Critical Alerts: Automated red-zone alerts for hospitals that reach zero bed capacity in critical categories.
🛠️ Technology Stack
Backend: Java Spring Boot, Spring Security, Spring Data JPA
Database: MySQL / TiDB Cloud (Relational data storage)
Frontend: HTML5, CSS3 (Bootstrap 5), JavaScript (ES6+)
Mapping: Leaflet.js & OpenStreetMap (Free, high-performance geospatial mapping)
Routing: Leaflet Routing Machine & Nominatim Geocoder
📂 Project Structure
code
Text
src/main/java/com/project/hospital_bed_monitor/
  ├── config/       # Security and API configurations
  ├── controller/   # REST Endpoints (Admin, Staff, Public)
  ├── dto/          # Data Transfer Objects for secure data exchange
  ├── entity/       # Database Models (User, Hospital, BedAvailability)
  ├── repository/   # JPA Repositories
  └── service/      # Business logic and User Details Service
src/main/resources/
  ├── static/       # Frontend HTML, CSS, and JS files
  └── application.properties # Configuration for DB and Port
⚙️ Setup & Installation
1. Prerequisites
Java 17 or higher
MySQL 8.0
Maven
2. Database Setup
Run the following commands in your MySQL workbench:
code
SQL
CREATE DATABASE hospital_db;
-- Note: The application will automatically create tables on the first run 
-- due to 'spring.jpa.hibernate.ddl-auto=update'
3. Application Configuration
Update src/main/resources/application.properties:
code
Properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=your_username
spring.datasource.password=your_password
4. Running the Application
code
Bash
mvn clean install
mvn spring-boot:run
The application will be live at http://localhost:8080.
👨‍🏫 Evaluation Highlights
Haversine Implementation: Custom mathematical logic for proximity sorting.
Role-Based Access Control (RBAC): Distinct permissions for Public, Staff, and Admin roles.
Traffic Buffer Logic: Implemented a 1.3x multiplier to OpenStreetMap data to provide realistic travel times.
Relational Integrity: Complex @OneToMany relationships with infinite recursion protection.
