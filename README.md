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
  - H2 Database

- **Design Patterns**
  - State Pattern (Booking states)
  - Factory Pattern (User creation)
  - Command Pattern (Bus operations)
  - Facade Pattern (Authentication)

- **Architecture**
  - Layered Architecture
  - RESTful API
  - SOLID Principles
  - GRASP Patterns

## 📦 Project Structure

```
src/main/java/com/example/busreservation/
├── config/           # Configuration classes
├── controllers/      # REST controllers
├── dto/             # Data Transfer Objects
├── factory/         # Factory pattern implementation
├── models/          # Entity classes
├── repositories/    # Data access layer
├── services/        # Business logic
├── state/           # State pattern implementation
└── commands/        # Command pattern implementation
```

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

## 🎯 Design Principles

### SOLID Principles
- **Single Responsibility**: Each class has one reason to change
- **Open/Closed**: Open for extension, closed for modification
- **Liskov Substitution**: Subtypes can replace their base types
- **Interface Segregation**: Clients shouldn't depend on unused interfaces
- **Dependency Inversion**: Depend on abstractions, not concretions

### GRASP Patterns
- **Information Expert**: Assign responsibilities to classes with the most information
- **Creator**: Assign creation responsibilities to classes that contain or aggregate
- **Controller**: Handle system events
- **Low Coupling**: Minimize dependencies between classes
- **High Cohesion**: Keep related responsibilities together

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
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

## 🔒 Security

- Spring Security integration
- Role-based access control
- Secure password storage with PasswordEncoder
- CSRF protection
- CORS configuration

## 📝 API Documentation

The API documentation is available through Swagger UI at:
`http://localhost:8080/swagger-ui.html`

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- SIMONNA ANNA DCOSTA
- SINCHANA AS 
- SHILPA M TALAWAR
- SPANDANA M POOJARY

## 🙏 Acknowledgments

- Spring Boot Team
- Open Source Community
