package com.example.busreservation.controllers;

import com.example.busreservation.models.Bus;
import com.example.busreservation.models.Booking;
import com.example.busreservation.models.User;
import com.example.busreservation.services.BusService;
import com.example.busreservation.services.BookingService;
import com.example.busreservation.services.UserService;
import com.example.busreservation.factory.BookingFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/passenger")
public class PassengerController {

    private final BookingService bookingService;
    private final BusService busService;
    private final UserService userService;

    @Autowired
    public PassengerController(BookingService bookingService, BusService busService, UserService userService) {
        this.bookingService = bookingService;
        this.busService = busService;
        this.userService = userService;
    }

    // Show dashboard
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        List<Booking> activeBookings = bookingService.getActiveBookingsByUsername(username);
        model.addAttribute("activeBookings", activeBookings);
        return "passenger_dashboard";
    }

    // Show available buses
    @GetMapping("/view-buses")
    public String showAvailableBuses(Model model) {
        try {
            model.addAttribute("buses", busService.getAllBuses());
            return "view_buses";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading buses: " + e.getMessage());
            return "error";
        }
    }

    // Show booking form for selected bus
    @GetMapping("/book/{id}")
    public String showBookingForm(@PathVariable("id") Long busId, Model model) {
        try {
            Bus selectedBus = busService.getBusById(busId);
            if (selectedBus == null) {
                model.addAttribute("error", "Bus not found");
                return "error";
            }
            
            // Get all booked seats for this bus
            List<Booking> activeBookings = bookingService.getActiveBookingsByBus(selectedBus);
            List<Integer> bookedSeats = new ArrayList<>();
            for (Booking booking : activeBookings) {
                if (booking.getSeatNumbers() != null) {
                    Arrays.stream(booking.getSeatNumbers().split(","))
                          .map(Integer::parseInt)
                          .forEach(bookedSeats::add);
                }
            }
            
            model.addAttribute("bus", selectedBus);
            model.addAttribute("bookedSeats", bookedSeats);
            return "booking-form";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading booking form: " + e.getMessage());
            return "error";
        }
    }

    // Handle ticket booking
    @PostMapping("/process-booking")
    public String processBooking(
        @RequestParam("busId") Long busId,
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("phone") String phone,
        @RequestParam("selectedSeats") String selectedSeats,
        @RequestParam(value = "bookingType", defaultValue = "REGULAR") String bookingType,
        Authentication authentication,
        Model model) {

        try {
            // Get the current user
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            
            // Get the selected bus
            Bus bus = busService.getBusById(busId);
            
            // Validate selected seats
            String[] seatArray = selectedSeats.split(",");
            int numberOfSeats = seatArray.length;
            
            // Check if seats are already booked
            List<Booking> activeBookings = bookingService.getActiveBookingsByBus(bus);
            Set<Integer> bookedSeats = new HashSet<>();
            for (Booking booking : activeBookings) {
                if (booking.getSeatNumbers() != null) {
                    Arrays.stream(booking.getSeatNumbers().split(","))
                          .map(Integer::parseInt)
                          .forEach(bookedSeats::add);
                }
            }
            
            // Check if any selected seat is already booked
            for (String seat : seatArray) {
                if (bookedSeats.contains(Integer.parseInt(seat))) {
                    model.addAttribute("error", "Seat " + seat + " is already booked. Please select different seats.");
                    return "error";
                }
            }
            
            // Calculate total amount based on booking type and number of seats
            double baseFare = bus.getFare();
            double totalAmount = switch (bookingType.toUpperCase()) {
                case "PRIORITY" -> baseFare * numberOfSeats * 1.2; // 20% extra for priority booking
                default -> baseFare * numberOfSeats; // Regular booking
            };
            
            // Use BookingFactory to create the booking
            Booking booking = BookingFactory.createBooking(
                bookingType,
                user,
                bus,
                name,
                email,
                phone,
                numberOfSeats,
                selectedSeats,
                totalAmount
            );
            
            // Save the booking
            bookingService.saveBooking(booking);
            
            // Update bus available seats
            bus.setSeatsAvailable(bus.getSeatsAvailable() - numberOfSeats);
            busService.addBus(bus);
            
            return "redirect:/passenger/dashboard?success=true";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to process booking: " + e.getMessage());
            return "error";
        }
    }

    // View all bookings for the current user
    @GetMapping("/bookings")
    public String viewBookings(Model model) {
        try {
            // Get the current user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            List<Booking> bookings = bookingService.getBookingsByUser(userService.getUserByUsername(username));
            model.addAttribute("bookings", bookings);
            return "view_bookings";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading bookings: " + e.getMessage());
            return "error";
        }
    }

    // Cancel a booking
    @PostMapping("/bookings/cancel/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Booking booking = bookingService.getBookingById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));
            
            if (!booking.getUser().getUsername().equals(username)) {
                model.addAttribute("error", "Unauthorized to cancel this booking");
                return "error";
            }

            if (!booking.getStatus().equals("ACTIVE")) {
                model.addAttribute("error", "Only active bookings can be cancelled");
                return "error";
            }

            // Update bus seats
            Bus bus = booking.getBus();
            bus.setSeatsAvailable(bus.getSeatsAvailable() + booking.getSeatsBooked());
            busService.updateBus(bus.getId(), bus);

            // Set booking status to CANCELLED
            booking.setStatus("CANCELLED");
            bookingService.saveBooking(booking);

            return "redirect:/passenger/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Error cancelling booking: " + e.getMessage());
            return "error";
        }
    }

    // Show cancel booking page
    @GetMapping("/cancel-booking")
    public String showCancelBookingPage(Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            List<Booking> activeBookings = bookingService.getActiveBookingsByUsername(username);
            model.addAttribute("activeBookings", activeBookings);
            return "cancel_booking";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading cancel booking page: " + e.getMessage());
            return "error";
        }
    }

    // View latest ticket
    @GetMapping("/view-ticket")
    public String viewLatestTicket(Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            
            List<Booking> bookings = bookingService.getBookingsByUser(user);
            if (bookings.isEmpty()) {
                model.addAttribute("error", "No bookings found");
                return "error";
            }
            
            // Get the latest booking (assuming bookings are ordered by date)
            Booking latestBooking = bookings.stream()
                .max((b1, b2) -> b1.getId().compareTo(b2.getId()))
                .orElseThrow(() -> new RuntimeException("No booking found"));
            
            model.addAttribute("booking", latestBooking);
            return "view-ticket";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading ticket: " + e.getMessage());
            return "error";
        }
    }
}
