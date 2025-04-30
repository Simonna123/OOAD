package com.example.busreservation.state;

import com.example.busreservation.models.Booking;

public interface BookingState {
    void handleCancellation(Booking booking);
    String getStateName();
} 