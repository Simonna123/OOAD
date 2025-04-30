package com.example.busreservation.factory;

import com.example.busreservation.models.Booking;
import com.example.busreservation.models.Bus;
import com.example.busreservation.models.User;
import com.example.busreservation.state.ActiveBookingState;

public class BookingFactory {
    
    public static Booking createBooking(String bookingType, User user, Bus bus, String passengerName, 
            String passengerEmail, String passengerPhone, int seatsBooked, String seatNumbers, double totalAmount) {
        
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBus(bus);
        booking.setPassengerName(passengerName);
        booking.setPassengerEmail(passengerEmail);
        booking.setPassengerPhone(passengerPhone);
        booking.setSeatsBooked(seatsBooked);
        booking.setSeatNumbers(seatNumbers);
        booking.setTotalAmount(totalAmount);
        booking.setBookingType(bookingType);
        booking.setState(new ActiveBookingState());
        booking.setStatus("ACTIVE");
        
        return booking;
    }
    
    public static Booking createRegularBooking(User user, Bus bus, String passengerName, 
            String passengerEmail, String passengerPhone, int seatsBooked, String seatNumbers, double totalAmount) {
        return createBooking("REGULAR", user, bus, passengerName, passengerEmail, passengerPhone, 
                seatsBooked, seatNumbers, totalAmount);
    }
    
    public static Booking createPriorityBooking(User user, Bus bus, String passengerName, 
            String passengerEmail, String passengerPhone, int seatsBooked, String seatNumbers, double totalAmount) {
        return createBooking("PRIORITY", user, bus, passengerName, passengerEmail, passengerPhone, 
                seatsBooked, seatNumbers, totalAmount);
    }
} 