package com.example.busreservation.controllers;

import com.example.busreservation.commands.AddBusCommand;
import com.example.busreservation.commands.EditBusCommand;
import com.example.busreservation.commands.DeleteBusCommand;
import com.example.busreservation.commands.BusCommandExecutor;
import com.example.busreservation.models.Bus;
import com.example.busreservation.services.BusService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusService busService;

    @Autowired
    private BusCommandExecutor executor;

    // Show the add bus form
    @GetMapping("/add")
    public String showAddBusForm(Model model) {
        model.addAttribute("bus", new Bus());
        return "add-bus";
    }

    // Handle adding a bus
    @PostMapping("/add")
    public String addBus(@ModelAttribute("bus") Bus bus) {
        executor.executeCommand(new AddBusCommand(busService, bus));
        return "redirect:/admin/dashboard";
    }

    // Show the edit bus form
    @GetMapping("/admin/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Bus bus = busService.getBusById(id);
        model.addAttribute("bus", bus);
        return "edit_bus";
    }

    // Handle updating a bus
    @PostMapping("/admin/edit/{id}")
    public String updateBus(@PathVariable Long id, @ModelAttribute("bus") Bus bus) {
        executor.executeCommand(new EditBusCommand(busService, id, bus));
        return "redirect:/admin/dashboard";
    }

    // Handle deleting a bus
    @GetMapping("/delete/{id}")
    public String deleteBus(@PathVariable Long id) {
        executor.executeCommand(new DeleteBusCommand(busService, id));
        return "redirect:/admin/dashboard";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // View all buses
    @GetMapping("/view")
    public String getAllBuses(Model model, @RequestParam(required = false) String message) {
        List<Bus> buses = busService.getAllBuses();
        model.addAttribute("buses", buses);
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "view-buses";
    }
}
