package com.hotel.service;


import com.hotel.entity.Booking;
import com.hotel.entity.Room;
import com.hotel.entity.User;
import com.hotel.enums.RoomType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Service {

    private final List<Room> rooms = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    public void setRoom(int roomNumber, RoomType type, int pricePerNight) {
        Room room = rooms.stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
        if (room == null) {
            rooms.add(new Room(roomNumber, type, pricePerNight));
        } else {
            room.setType(type);
            room.setPricePerNight(pricePerNight);
        }
    }

    public void setUser(int userId, int balance) {
        User user = users.stream()
                .filter(u -> u.getUserId() == userId)
                .findFirst()
                .orElse(null);
        if (user == null) {
            users.add(new User(userId, balance));
        } else {
            user.setBalance(balance);
        }
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        User user = users.stream().filter(u -> u.getUserId() == userId).findFirst().orElse(null);
        Room room = rooms.stream().filter(r -> r.getRoomNumber() == roomNumber).findFirst().orElse(null);
         List<Booking> bookings = new ArrayList<>();

        if (user == null || room == null) {
            System.out.println("Utilisateur ou chambre introuvable ! (User: " + userId + ", Room: " + roomNumber + ")");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Nombre de nuits
        long diff = (checkOut.getTime() - checkIn.getTime()) / (1000 * 60 * 60 * 24);
        if (diff <= 0) {
            System.out.println(" Dates invalides pour User " + userId + ", Chambre " + roomNumber +
                    " (" + sdf.format(checkIn) + " au " + sdf.format(checkOut) + ")");
            return;
        }

        int totalPrice = (int) (diff * room.getPricePerNight());

        if (user.getBalance() < totalPrice) {
            System.out.println(" Solde insuffisant pour User " + userId + ", Chambre " + roomNumber +
                    " | Total prix: " + totalPrice + ", Solde: " + user.getBalance());
            return;
        }

        for (Booking b : room.getBookings()) {
            if (!(checkOut.before(b.getCheckIn()) || checkIn.after(b.getCheckOut()))) {
                System.out.println(" Chambre " + roomNumber + " non disponible pour User " + userId +
                        " (" + sdf.format(checkIn) + " au " + sdf.format(checkOut) + ")");
                return;
            }
        }
        Booking booking = new Booking(user, room, checkIn, checkOut, totalPrice);
        room.getBookings().add(booking);
        bookings.add(booking);
        user.setBalance(user.getBalance() - totalPrice);

        System.out.println("Réservation réussie pour User " + userId + ", Chambre " + roomNumber +
                " (" + sdf.format(checkIn) + " au " + sdf.format(checkOut) + ") | Total prix: " + totalPrice);    }


    public void printAll() {
        List<Room> reversedRooms = new ArrayList<>(rooms);
        Collections.reverse(reversedRooms);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Room room : reversedRooms) {
            System.out.println("Chambre " + room.getRoomNumber() +
                    " | Type: " + room.getType() +
                    " | Prix/nuit: " + room.getPricePerNight());

            List<Booking> bookingsForRoom = room.getBookings();
            if (bookingsForRoom.isEmpty()) {
                System.out.println("  Aucune réservation.");
            } else {
                for (int i = 0; i < bookingsForRoom.size(); i++) {
                    Booking b = bookingsForRoom.get(i);
                    System.out.println("  Réservation :" + (i + 1));
                    System.out.println("    Utilisateur : " + b.getUser().getUserId());
                    System.out.println("    Check-in    : " + sdf.format(b.getCheckIn()));
                    System.out.println("    Check-out   : " + sdf.format(b.getCheckOut()));
                    System.out.println("    Total prix  : " + b.getTotalPrice());
                }
            }
            System.out.println();
        }
    }



    public void printAllUsers() {
        List<User> reversedUsers = new ArrayList<>(users);
        Collections.reverse(reversedUsers);

        for (User u : reversedUsers) {
            System.out.println("User " + u.getUserId() + " | Solde restant: " + u.getBalance());
        }
    }

}