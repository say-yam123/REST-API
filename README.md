## 📋 Overview

This lab focused on designing, implementing, and testing RESTful APIs for our application using Spring Boot. The goal was to enable communication between the frontend, backend, and database by exposing endpoints that perform business operations and data management.

## ⚙️ Prerequisites

To run and test the components from this lab, ensure the following are installed and configured:

**Programming Languages:** Java 17+  

**Frameworks & Libraries:**  
- Spring Boot  
- Spring Web  
- Spring Data JPA  
- Maven  

**Database:**  
- MySQL Server (running and accessible)  

**API Testing Tools:**  
- Postman / Browser / cURL  

---

# 🔬 Lab 10: API Development and Integration

**Objective:** Create and test REST APIs to manage application data and establish communication between the backend services and database.

## What we did:

### API Design
Designed RESTful endpoints following standard HTTP methods (`GET`, `POST`, `PUT`, `DELETE`) to perform CRUD operations for application entities.

### Controller Implementation
Developed Spring Boot controller classes to handle incoming client requests and route them to the appropriate services.

### Service Layer Integration
Implemented business logic within service classes to process requests before interacting with the database layer.

### Database Connectivity
Integrated the APIs with the MySQL database using Spring Data JPA and repository interfaces for seamless data persistence.

### API Testing
Used Postman and browser-based requests to verify endpoint functionality, ensuring accurate request handling and response generation.

### Data Exchange
Implemented JSON-based request and response handling to enable efficient communication between client applications and backend services.

### Validation and Error Handling
Added input validation and exception handling mechanisms to ensure reliable and secure API behavior.

### Reporting
Successfully tested all endpoints and documented API request structures, response formats, and expected outputs for future development and maintenance.