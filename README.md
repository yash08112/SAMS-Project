# SAMS - Student Attendance Management System (Spring Boot)

A Spring Boot web application for managing student attendance, converted from a single-file Java OOP project into a deployable Maven structure.

## Tech Stack

- Java 17
- Spring Boot (Web + Thymeleaf)
- Maven
- TailwindCSS + Lucide (CDN in frontend)

## Project Structure

```text
.
├── pom.xml
├── Procfile
├── system.properties
└── src
    └── main
        ├── java/com/sams
        │   ├── SamsApplication.java
        │   ├── config/
        │   ├── dao/
        │   ├── exception/
        │   ├── model/
        │   ├── service/
        │   ├── util/
        │   └── web/
        └── resources
            ├── application.properties
            └── templates/index.html