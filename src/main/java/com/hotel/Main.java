package com.hotel;
import com.hotel.enums.RoomType;
import com.hotel.service.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws ParseException {

        Service service = new Service();

        // Créer 3 chambres
        service.setRoom(1, RoomType.STANDARD_SUITE, 1000);
        service.setRoom(2, RoomType.JUNIOR_SUITE, 2000);
        service.setRoom(3, RoomType.MASTER_SUITE, 3000);

        // Créer 2 utilisateurs
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Réservations
        // User 1 réserve Room 2 du 30/06/2026 au 07/07/2026
        Date checkIn1 = sdf.parse("30/06/2026");
        Date checkOut1 = sdf.parse("07/07/2026");
        service.bookRoom(1, 2, checkIn1, checkOut1);

        // User 1 essaie de réserver Room 2 du 07/07/2026 au 30/06/2026 (dates invalides)
        Date checkIn2 = sdf.parse("07/07/2026");
        Date checkOut2 = sdf.parse("30/06/2026");
        service.bookRoom(1, 2, checkIn2, checkOut2);

        // User 1 réserve Room 1 du 07/07/2026 au 08/07/2026
        Date checkIn3 = sdf.parse("07/07/2026");
        Date checkOut3 = sdf.parse("08/07/2026");
        service.bookRoom(1, 1, checkIn3, checkOut3);

        // User 2 réserve Room 1 du 07/07/2026 au 09/07/2026
        Date checkIn4 = sdf.parse("07/07/2026");
        Date checkOut4 = sdf.parse("09/07/2026");
        service.bookRoom(2, 1, checkIn4, checkOut4);

        // User 2 réserve Room 3 du 07/07/2026 au 08/07/2026
        Date checkIn5 = sdf.parse("07/07/2026");
        Date checkOut5 = sdf.parse("08/07/2026");
        service.bookRoom(2, 3, checkIn5, checkOut5);

        service.setRoom(1, RoomType.MASTER_SUITE, 10000);


        System.out.println("\n===== Toutes les chambres et réservations =====");
        service.printAll();

        System.out.println("\n===== Tous les utilisateurs =====");
        service.printAllUsers();
    }
}
