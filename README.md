# Bus Reservation System

A comprehensive bus reservation system built with Spring Boot, implementing modern software design principles and patterns.

## 🚀 Features

- **User Management**
  - User registration and authentication
  - Role-based access (Admin, Passenger)
  - Secure password handling with Spring Security

- **Bus Management**
  - Add, edit, and delete buses
  - Manage bus routes and schedules
  - Track bus availability and seat management

- **Booking System**
  - Create and manage bookings
  - State-based booking management
  - Cancellation handling
  - Seat selection and management

- **Security**
  - Spring Security integration
  - Role-based access control (ROLE_ADMIN, ROLE_USER)
  - Secure password storage
  - CSRF protection

## 🛠️ Technical Stack

- **Backend**
  - Spring Boot 3.x
  - Spring Security
  - Spring Data JPA
  - MySQL Database

- **Design Patterns**
  - State Pattern (Cancellation)
  - Factory Pattern (Booking)
  - Command Pattern (Bus operations)
  - Facade Pattern (Authentication)

- **Architecture**
  - Layered Architecture
  - RESTful API
  - SOLID Principles
  - GRASP Patterns


## 🔑 Key Classes

- **Models**
  - `User` (Abstract)
  - `Admin` (extends User)
  - `Passenger` (extends User)
  - `Booking`
  - `Bus`
  - `Route`

- **Controllers**
  - `AuthController`
  - `AdminController`
  - `PassengerController`
  - `RouteController`
  - `BookingController`

- **Services**
  - `UserService`
  - `BookingService`
  - `BusService`
  - `AuthFacade`

## 🚦 Getting Started

1. **Prerequisites**
   - Java 17 or higher
   - Maven
   - Spring Boot 3.x

2. **Installation**
   ```bash
   git clone [repository-url]
   cd bus-reservation-system
   mvn clean install
   ```

3. **Running the Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Accessing the Application**
   - API Base URL: `http://localhost:8080`



## 👥 Authors

- SIMONNA ANNA DCOSTA
- SINCHANA AS 
- SHILPA M TALAWAR
- SPANDANA M POOJARY


