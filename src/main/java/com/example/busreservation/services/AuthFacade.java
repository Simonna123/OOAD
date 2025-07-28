package com.example.busreservation.services;

import com.example.busreservation.models.User;
import com.example.busreservation.models.Passenger;
import com.example.busreservation.repositories.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthFacade {

    private final UserRepository        userRepository;
    private final PasswordEncoder       passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthFacade(UserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      AuthenticationManager authenticationManager) {
        this.userRepository       = userRepository;
        this.passwordEncoder      = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new passenger (non‑admin) user.
     */
    public User register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        Passenger user = new Passenger(username, passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    /**
     * Delegates authentication to Spring's AuthenticationManager,
     * which in turn will call your UserService (UserDetailsService).
     */
    public Authentication login(String username, String password) {
        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(token);
    }
}
