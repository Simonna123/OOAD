-- Drop existing tables if they exist
DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS bus;
DROP TABLE IF EXISTS user;

-- Create User Table
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN NOT NULL DEFAULT FALSE,
    role VARCHAR(255) NOT NULL DEFAULT 'ROLE_USER',
    user_type VARCHAR(255) NOT NULL DEFAULT 'USER',
    dtype VARCHAR(31) NOT NULL DEFAULT 'User'
);

-- Create Bus Table
CREATE TABLE bus (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bus_name VARCHAR(100) NOT NULL,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    total_seats INT NOT NULL DEFAULT 40,
    seats_available INT NOT NULL,
    fare DECIMAL(10,2) NOT NULL,
    bus_type VARCHAR(50) NOT NULL
);

-- Create Booking Table
CREATE TABLE booking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    bus_id INT NOT NULL,
    passenger_name VARCHAR(100) NOT NULL,
    passenger_email VARCHAR(100),
    passenger_phone VARCHAR(20),
    seats_booked INT NOT NULL,
    seat_numbers VARCHAR(255),
    booking_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (bus_id) REFERENCES bus(id) ON DELETE CASCADE
); 