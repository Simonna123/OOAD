package com.example.busreservation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Passenger")
public class Passenger extends User {
    
    public Passenger() {
        super();
        this.setAdmin(false); // Passengers are not admins
    }

    public Passenger(String username, String password) {
        super(username, password, false); // Passengers are always non-admin
    }
}
