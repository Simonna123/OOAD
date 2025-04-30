package com.example.busreservation.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.example.busreservation.state.BookingState;
import com.example.busreservation.state.ActiveBookingState;
import com.example.busreservation.state.CancelledBookingState;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @Column(name = "passenger_name", nullable = false, length = 100)
    private String passengerName;

    @Column(name = "passenger_email", length = 100)
    private String passengerEmail;

    @Column(name = "passenger_phone", length = 20)
    private String passengerPhone;

    @Column(name = "seats_booked", nullable = false)
    private int seatsBooked;

    @Column(name = "seat_numbers")
    private String seatNumbers;

    @Column(name = "booking_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime bookingTime;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "status")
    private String status = "ACTIVE";

    @Column(name = "booking_type", nullable = false)
    private String bookingType = "REGULAR";

    @Transient
    private BookingState state;

    // Constructors
    public Booking() {
        // Initialize with ActiveBookingState by default
        this.state = new ActiveBookingState();
        this.status = "ACTIVE";
    }

    public Booking(User user, Bus bus, String passengerName, String passengerEmail, String passengerPhone,
            int seatsBooked, double totalAmount) {
        this.user = user;
        this.bus = bus;
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
        this.passengerPhone = passengerPhone;
        this.seatsBooked = seatsBooked;
        this.totalAmount = totalAmount;
        this.bookingTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public String getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(String seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public void setState(BookingState state) {
        this.state = state;
        this.status = state.getStateName();
    }

    public BookingState getState() {
        // Reconstruct state from status if null
        if (state == null) {
            state = switch (status) {
                case "CANCELLED" -> new CancelledBookingState();
                default -> new ActiveBookingState();
            };
        }
        return state;
    }

    public void cancelBooking() {
        getState().handleCancellation(this);
    }

    public String getBookingState() {
        return getState().getStateName();
    }

    // Optional: @PrePersist method to set booking time automatically before
    // persisting the entity
    @PrePersist
    public void prePersist() {
        if (this.bookingTime == null) {
            this.bookingTime = LocalDateTime.now();
        }
    }
}
