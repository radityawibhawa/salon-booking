package com.booking.repositories;

import java.util.ArrayList;
import java.util.List;


import com.booking.models.Reservation;

public class ReservationRepository {
    private static List<Reservation> listReservation = new ArrayList<>();

    public static List<Reservation> getAllReservation() {
        return listReservation;
    }
}
