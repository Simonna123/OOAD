package com.example.busreservation.controllers;

import com.example.busreservation.models.Booking;
import com.example.busreservation.models.Bus;
import com.example.busreservation.models.User;
import com.example.busreservation.models.TicketForm;
import com.example.busreservation.services.BookingService;
import com.example.busreservation.services.BusService;
import com.example.busreservation.services.TicketService;
import com.example.busreservation.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BusService busService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @GetMapping("/view")
    public String viewAllBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "view-booking";
    }

    @GetMapping("/book-ticket")
    public String showBookingForm(Model model) {
        List<Bus> buses = busService.getAllBuses();
        model.addAttribute("buses", buses);
        model.addAttribute("ticketForm", new TicketForm());
        return "book-ticket";
    }

    @GetMapping("/passenger/{id}")
    public String bookForPassenger(@PathVariable("id") Long passengerId, Model model) { // ✅ Long instead of Integer
        List<Bus> buses = busService.getAllBuses();
        model.addAttribute("buses", buses);

        TicketForm ticketForm = new TicketForm();
        ticketForm.setPassengerId(passengerId);
        model.addAttribute("ticketForm", ticketForm);

        return "book-ticket";
    }

    @PostMapping("/book-ticket")
    public String bookTicket(@ModelAttribute TicketForm ticketForm, RedirectAttributes redirectAttributes) {
        try {
            ticketService.book(ticketForm);
            redirectAttributes.addFlashAttribute("success", "Ticket booked successfully!");
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Booking failed: " + e.getMessage());
            return "redirect:/bookings/book-ticket";
        }
    }

    @GetMapping("/confirm")
    public String confirmBooking(Model model) {
        model.addAttribute("message", "Booking Confirmed Successfully!");
        return "booking_confirmation";
    }

    @GetMapping("/user/{userId}")
    public String getBookingsByUser(@PathVariable("userId") Long userId, Model model) { // ✅ Long instead of Integer
        User user = userService.getUserById(userId);
        List<Booking> bookings = bookingService.getBookingsByUser(user);
        model.addAttribute("bookings", bookings);
        return "view-user-bookings";
    }
}
