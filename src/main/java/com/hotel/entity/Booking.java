package com.hotel.entity;

import java.util.Date;

public class Booking {
    private final User user;
    private final Room room;
    private final Date checkIn;
    private final Date checkOut;
    private final int totalPrice;

    public Booking(User user, Room room, Date checkIn, Date checkOut, int totalPrice) {
        this.user = user;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
    }

    public User getUser() { return user; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }
    public int getTotalPrice() { return totalPrice; }
}
