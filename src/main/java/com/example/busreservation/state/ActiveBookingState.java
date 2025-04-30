package com.example.busreservation.state;

import com.example.busreservation.models.Booking;
import com.example.busreservation.models.Bus;

public class ActiveBookingState implements BookingState {
    
    @Override
    public void handleCancellation(Booking booking) {
        // Update bus seats
        Bus bus = booking.getBus();
        bus.setSeatsAvailable(bus.getSeatsAvailable() + booking.getSeatsBooked());
        
        // Update booking status
        booking.setStatus("CANCELLED");
        booking.setState(new CancelledBookingState());
    }
    
    @Override
    public String getStateName() {
        return "ACTIVE";
    }
} 