package com.example.busreservation.controllers;

import com.example.busreservation.models.Booking;
import com.example.busreservation.models.Bus;
import com.example.busreservation.services.BookingService;
import com.example.busreservation.services.BusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class BookingFormController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BusService busService;

    @PostMapping("/booking/book")
    public String bookBus(
            @RequestParam("busId") Long busId,
            @RequestParam("passengerName") String passengerName, // If you are using this later
            @RequestParam("seats") int seats,
            Model model) {

        // Fetch the Bus entity using busId
        Bus bus = busService.getBusById(busId);
        if (bus == null) {
            model.addAttribute("error", "Bus not found!");
            return "error"; // Create error.html if not already there
        }

        // Create and save booking
        Booking booking = new Booking();
        booking.setBus(bus);
        booking.setSeatsBooked(seats);
        booking.setBookingTime(LocalDateTime.now());

        bookingService.saveBooking(booking);

        // Add confirmation message to the model
        model.addAttribute("message", "Booking successful!");
        return "booking_confirmation"; // Create booking_confirmation.html
    }
}
