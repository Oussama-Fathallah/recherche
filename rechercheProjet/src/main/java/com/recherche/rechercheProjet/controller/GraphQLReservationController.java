package com.recherche.rechercheProjet.controller;


import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.recherche.rechercheProjet.model.Reservation;
import com.recherche.rechercheProjet.service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class GraphQLReservationController {

    private final ReservationService reservationService;

    @QueryMapping
    public List<Reservation> allReservations() {
        return reservationService.getAllReservations();
    }

    @QueryMapping
    public Reservation reservationById(Long id) {
        // Obtenir l'objet optionnel et lever une exception si absent
        return reservationService.getReservationById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }

    @MutationMapping
    public Reservation createReservation(String clientName, String roomType, String InDate, String OutDate) {
        try {
            Date checkInDate = parseDate(InDate);
            Date checkOutDate = parseDate(OutDate);

            Reservation reservation = new Reservation();
            reservation.setClientName(clientName);
            reservation.setRoomType(roomType);
            reservation.setCheckInDate(checkInDate);
            reservation.setCheckOutDate(checkOutDate);

            return reservationService.createReservation(reservation);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format. Please use 'yyyy-MM-dd'.");
        }
    }

    @MutationMapping
    public boolean deleteReservation(Long id) {
        reservationService.deleteReservation(id);
        return true;
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }
}
