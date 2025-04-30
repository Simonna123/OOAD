package com.example.busreservation.services;

import com.example.busreservation.models.Booking;
import com.example.busreservation.models.Bus;
import com.example.busreservation.models.TicketForm;
import com.example.busreservation.models.User;
import com.example.busreservation.repositories.BookingRepository;
import com.example.busreservation.repositories.BusRepository;
import com.example.busreservation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TicketService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void book(TicketForm form) {
        // Fetch the selected bus using the busId from the form
        Bus selectedBus = busRepository.findById(form.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + form.getBusId()));

        // Check if there are enough seats available BEFORE proceeding with booking
        int requestedSeats = form.getNumberOfSeats();
        if (selectedBus.getSeatsAvailable() < requestedSeats) {
            throw new RuntimeException("Not enough seats available. Only " + selectedBus.getSeatsAvailable() + " seats left.");
        }

        // Calculate total amount
        double totalAmount = selectedBus.getFare() * requestedSeats;

        // Optional: Set the user if passengerId is provided
        User passenger = null;
        if (form.getPassengerId() != null) {
            passenger = userRepository.findById(form.getPassengerId()).orElse(null);
        }

        // Update the bus seat availability FIRST
        selectedBus.setSeatsAvailable(selectedBus.getSeatsAvailable() - requestedSeats);
        busRepository.save(selectedBus);

        // Create a new booking
        Booking booking = new Booking();
        booking.setBus(selectedBus);
        booking.setSeatsBooked(requestedSeats);
        booking.setBookingTime(LocalDateTime.now());
        booking.setPassengerName(form.getPassengerName());
        booking.setPassengerEmail(form.getEmail());
        booking.setPassengerPhone(form.getPhone());
        booking.setTotalAmount(totalAmount);
        if (passenger != null) {
            booking.setUser(passenger);
        }

        // Save the booking in the repository
        bookingRepository.save(booking);
    }
}
