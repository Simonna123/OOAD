package com.example.busreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.busreservation")
public class BusReservationSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusReservationSystemApplication.class, args);
    }
}
