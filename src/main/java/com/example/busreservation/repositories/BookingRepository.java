package com.example.busreservation.repositories;

import com.example.busreservation.models.Booking;
import com.example.busreservation.models.User;
import com.example.busreservation.models.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime; // Import LocalDateTime
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find bookings by User (passenger)
    List<Booking> findByUser(User user);

    // Find bookings by Bus
    List<Booking> findByBus(Bus bus);

    // Find bookings by number of seats booked
    List<Booking> findBySeatsBooked(int seatsBooked);

    // Find bookings by booking time within a range (startDate to endDate)
    List<Booking> findByBookingTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find bookings by user and status
    List<Booking> findByUserAndStatus(User user, String status);

    List<Booking> findByBusAndStatus(Bus bus, String status);
}
