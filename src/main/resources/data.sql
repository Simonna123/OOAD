-- Insert admin user
INSERT INTO user (username, password, is_admin, role, user_type, dtype)
VALUES ('admin', 'admin123', TRUE, 'ROLE_ADMIN', 'ADMIN', 'Admin');

-- Insert sample passenger users
INSERT INTO user (username, password, is_admin, role, user_type, dtype)
VALUES 
('john', 'john123', FALSE, 'ROLE_USER', 'USER', 'User'),
('sarah', 'sarah123', FALSE, 'ROLE_USER', 'USER', 'User'),
('mike', 'mike123', FALSE, 'ROLE_USER', 'USER', 'User'),
('test', 'test', FALSE, 'ROLE_USER', 'USER', 'User');

-- Insert sample bus data
INSERT INTO bus (bus_name, source, destination, departure_time, arrival_time, total_seats, seats_available, fare, bus_type) VALUES
-- Bangalore Routes
('Sunrise Express', 'Bangalore', 'Chennai', '2024-04-25 07:00:00', '2024-04-25 12:00:00', 40, 40, 1200.00, 'AC Sleeper'),
('Royal Express', 'Bangalore', 'Mumbai', '2024-04-25 20:00:00', '2024-04-26 08:00:00', 45, 45, 1800.00, 'AC Sleeper'),
('City Express', 'Bangalore', 'Hyderabad', '2024-04-25 09:00:00', '2024-04-25 18:00:00', 35, 35, 1500.00, 'AC Semi-Sleeper'),

-- Mumbai Routes
('Coastal Express', 'Mumbai', 'Goa', '2024-04-25 08:00:00', '2024-04-25 16:00:00', 40, 40, 1000.00, 'AC Seater'),
('Mumbai Express', 'Mumbai', 'Delhi', '2024-04-25 22:00:00', '2024-04-26 10:00:00', 45, 45, 2000.00, 'AC Sleeper'),

-- Delhi Routes
('Capital Express', 'Delhi', 'Jaipur', '2024-04-25 07:30:00', '2024-04-25 12:30:00', 35, 35, 800.00, 'Non-AC Seater'),
('Northern Express', 'Delhi', 'Shimla', '2024-04-25 06:00:00', '2024-04-25 15:00:00', 30, 30, 1200.00, 'AC Seater'),

-- Chennai Routes
('Southern Express', 'Chennai', 'Bangalore', '2024-04-25 08:00:00', '2024-04-25 13:00:00', 40, 40, 1200.00, 'AC Sleeper'),
('Coastal Star', 'Chennai', 'Kochi', '2024-04-25 10:00:00', '2024-04-25 22:00:00', 45, 45, 1600.00, 'AC Sleeper'),

-- Hyderabad Routes
('Deccan Express', 'Hyderabad', 'Bangalore', '2024-04-25 09:00:00', '2024-04-25 18:00:00', 35, 35, 1500.00, 'AC Semi-Sleeper'),
('Telangana Express', 'Hyderabad', 'Vijayawada', '2024-04-25 07:00:00', '2024-04-25 12:00:00', 40, 40, 900.00, 'Non-AC Seater');

-- Insert some sample bookings
INSERT INTO booking (user_id, bus_id, passenger_name, passenger_email, passenger_phone, seats_booked, booking_time, total_amount)
VALUES
(2, 1, 'John Doe', 'john@example.com', '9876543210', 2, '2024-04-20 10:00:00', 2400.00),
(3, 3, 'Sarah Smith', 'sarah@example.com', '9876543211', 1, '2024-04-20 11:00:00', 1500.00),
(4, 5, 'Mike Johnson', 'mike@example.com', '9876543212', 3, '2024-04-20 12:00:00', 3000.00); 