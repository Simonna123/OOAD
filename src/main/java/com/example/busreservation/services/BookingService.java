package com.example.busreservation.services;

import com.example.busreservation.models.Booking;
import com.example.busreservation.models.User;
import com.example.busreservation.models.Bus;
import com.example.busreservation.repositories.BookingRepository;
import com.example.busreservation.state.CancelledBookingState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime; // Import LocalDateTime
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BusService busService;

    // Save or update a booking
    @Transactional
    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    // Retrieve all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Retrieve bookings by a specific user
    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    // Retrieve bookings by a specific bus
    public List<Booking> getBookingsByBus(Bus bus) {
        return bookingRepository.findByBus(bus);
    }

    // Retrieve a specific booking by its ID
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    // Delete a booking by its ID
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    // Optional: Retrieve bookings by number of seats booked
    public List<Booking> getBookingsBySeatsBooked(int seatsBooked) {
        return bookingRepository.findBySeatsBooked(seatsBooked);
    }

    // Optional: Retrieve bookings within a specific time range
    public List<Booking> getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByBookingTimeBetween(startDate, endDate);
    }

    public List<Booking> getActiveBookingsByUsername(String username) {
        User user = userService.getUserByUsername(username);
        return bookingRepository.findByUserAndStatus(user, "ACTIVE");
    }

    public List<Booking> getActiveBookingsByBus(Bus bus) {
        return bookingRepository.findByBusAndStatus(bus, "ACTIVE");
    }

    @Transactional
    public void processBooking(Booking booking) {
        // Get the bus from the booking
        Bus bus = booking.getBus();
        
        // Check if there are enough seats available
        if (bus.getSeatsAvailable() < booking.getSeatsBooked()) {
            throw new RuntimeException("Not enough seats available");
        }
        
        // Update the bus's available seats
        bus.setSeatsAvailable(bus.getSeatsAvailable() - booking.getSeatsBooked());
        busService.addBus(bus); // Fixed: Using correct method name addBus instead of save
        
        // Save the booking
        bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {
        // Get the booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId)); // Fixed: Using orElseThrow instead of null check
        
        // Get the bus from the booking
        Bus bus = booking.getBus();
        
        // Update the bus's available seats
        bus.setSeatsAvailable(bus.getSeatsAvailable() + booking.getSeatsBooked());
        busService.addBus(bus); // Fixed: Using correct method name addBus instead of save
        
        // Update booking status
        booking.setState(new CancelledBookingState());
        bookingRepository.save(booking);
    }
}
