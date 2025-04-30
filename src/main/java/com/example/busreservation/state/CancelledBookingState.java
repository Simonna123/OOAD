package com.example.busreservation.state;

import com.example.busreservation.models.Booking;

public class CancelledBookingState implements BookingState {
    
    @Override
    public void handleCancellation(Booking booking) {
        // Cannot cancel an already cancelled booking
        throw new IllegalStateException("Booking is already cancelled");
    }
    
    @Override
    public String getStateName() {
        return "CANCELLED";
    }
} 