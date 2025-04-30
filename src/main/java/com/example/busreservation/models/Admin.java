package com.example.busreservation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Admin")
public class Admin extends User {

    public Admin() {
        super();
        this.setAdmin(true); // Admins always have admin privileges
    }

    public Admin(String username, String password) {
        super(username, password, true); // Admins are always set as admin
    }
}
