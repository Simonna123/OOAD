package com.example.busreservation.controllers;

import com.example.busreservation.commands.AddBusCommand;
import com.example.busreservation.commands.EditBusCommand;
import com.example.busreservation.commands.DeleteBusCommand;
import com.example.busreservation.commands.BusCommandExecutor;
import com.example.busreservation.models.Bus;
import com.example.busreservation.models.Booking;
import com.example.busreservation.models.User;
import com.example.busreservation.services.BusService;
import com.example.busreservation.services.BookingService;
import com.example.busreservation.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BusService busService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusCommandExecutor executor;

    // Admin home page
    @GetMapping("/home")
    public String showAdminHome() {
        return "admin_home";
    }

    // Admin dashboard - manage buses
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("bus", new Bus());
        model.addAttribute("buses", busService.getAllBuses());
        return "admin_dashboard";
    }

    // Add a new bus
    @PostMapping("/add")
    public String addBus(@ModelAttribute("bus") Bus bus, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("buses", busService.getAllBuses());
            return "admin_dashboard";
        }
        
        try {
            // Validate departure and arrival times
            if (bus.getDepartureTime() != null && bus.getArrivalTime() != null) {
                if (bus.getArrivalTime().isBefore(bus.getDepartureTime())) {
                    return "redirect:/admin/dashboard?error=Arrival time must be after departure time";
                }
            }
            
            // Set total seats equal to available seats for new buses
            bus.setTotalSeats(bus.getSeatsAvailable());
            
            // Save the bus
            executor.executeCommand(new AddBusCommand(busService, bus));
            return "redirect:/admin/dashboard?success=Bus added successfully";
        } catch (Exception e) {
            return "redirect:/admin/dashboard?error=Failed to add bus: " + e.getMessage();
        }
    }

    // Edit bus form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Bus bus = busService.getBusById(id);
            if (bus == null) {
                return "redirect:/admin/dashboard?error=Bus not found";
            }
            model.addAttribute("bus", bus);
            return "edit_bus";
        } catch (Exception e) {
            return "redirect:/admin/dashboard?error=Error loading bus: " + e.getMessage();
        }
    }

    // Update a bus
    @PostMapping("/edit/{id}")
    public String updateBus(@PathVariable Long id, @ModelAttribute("bus") Bus bus, BindingResult result) {
        if (result.hasErrors()) {
            return "edit_bus";
        }
        
        try {
            // Validate departure and arrival times
            if (bus.getDepartureTime() != null && bus.getArrivalTime() != null) {
                if (bus.getArrivalTime().isBefore(bus.getDepartureTime())) {
                    return "redirect:/admin/dashboard?error=Arrival time must be after departure time";
                }
            }
            
            executor.executeCommand(new EditBusCommand(busService, id, bus));
            return "redirect:/admin/dashboard?success=Bus updated successfully";
        } catch (Exception e) {
            return "redirect:/admin/dashboard?error=Failed to update bus: " + e.getMessage();
        }
    }

    // Delete a bus
    @GetMapping("/delete/{id}")
    public String deleteBus(@PathVariable Long id) {
        try {
            executor.executeCommand(new DeleteBusCommand(busService, id));
            return "redirect:/admin/dashboard?success=Bus deleted successfully";
        } catch (Exception e) {
            return "redirect:/admin/dashboard?error=Failed to delete bus: " + e.getMessage();
        }
    }

    // View all bookings
    @GetMapping("/bookings")
    public String viewAllBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "booking_list";
    }

    // View all users
    @GetMapping("/users")
    public String viewAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "view_users";
    }

    // ✅ DELETE user and redirect to admin home
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/home";
    }
}
