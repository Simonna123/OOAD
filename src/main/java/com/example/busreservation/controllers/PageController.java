package com.example.busreservation.controllers;

import com.example.busreservation.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    private BusService busService;

    @GetMapping("/book-bus")
    public String showBookingPage(Model model) {
        model.addAttribute("buses", busService.getAllBuses());
        return "bus_book"; // maps to bus_book.html inside templates
    }
}
