package com.example.busreservation.controllers;

import com.example.busreservation.services.AuthFacade;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthFacade authFacade;

    public AuthController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    /**
     * Show the custom login page.
     * Spring Security will POST to /login for authentication.
     */
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error,
                                @RequestParam(required = false) String logout,
                                @RequestParam(required = false) String registered,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("msg", "You have been logged out.");
        }
        if (registered != null) {
            model.addAttribute("msg", "Registration successful. Please log in.");
        }
        return "login";
    }

    /**  
     * Show the registration page.
     */
    @GetMapping("/register")
    public String showRegisterPage(@RequestParam(required = false) String error,
                                   Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "register";
    }

    /**
     * Handle registration form submission.
     * Delegates to AuthFacade.register().
     */
    @PostMapping("/register")
    public String handleRegistration(@RequestParam String username,
                                     @RequestParam String password,
                                     Model model) {
        try {
            authFacade.register(username, password);
            // append ?registered so login page shows success msg
            return "redirect:/login?registered";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    /**
     * After successful login, redirect based on role.
     * Configured as Spring Security successHandler → /default-success
     */
    @GetMapping("/default-success")
    public String handleLoginSuccess(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return isAdmin
            ? "redirect:/admin/home"
            : "redirect:/passenger/dashboard";
    }
}
