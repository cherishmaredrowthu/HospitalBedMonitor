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
Database: MySQL 
Frontend: HTML5, CSS3 (Bootstrap 5), JavaScript (ES6+)
Mapping: Leaflet.js, OpenStreetMap
Routing: Leaflet Routing Machine, Nominatim Geocoder

📂 Project Structure
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
- Java 17+
- MySQL 8+
- Maven

2. Database Setup
CREATE DATABASE hospital_db;

3. Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=your_username
spring.datasource.password=your_password

4. Run Application
mvn clean install
mvn spring-boot:run

Access: http://localhost:8080

👨‍🏫 Evaluation Highlights
Haversine Formula implementation
Role-Based Access Control
Traffic Buffer Logic (1.3x multiplier)
Optimized OneToMany relationships

📌 Future Enhancements
Mobile app
Notifications
AI-based prediction
Volunteer integration

🤝 Contributing
Contributions are welcome.

📜 License
For academic and educational purposes.

