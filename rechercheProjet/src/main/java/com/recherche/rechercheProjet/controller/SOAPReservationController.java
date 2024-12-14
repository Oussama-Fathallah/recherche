package com.recherche.rechercheProjet.controller;


import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;

import com.recherche.rechercheProjet.model.Reservation;
import com.recherche.rechercheProjet.service.ReservationService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Endpoint
public class SOAPReservationController {

	@Autowired
    private ReservationService reservationService;


    @WebMethod
    public List<Reservation> getReservations() {
        return reservationService.getAllReservations();
    }

    @WebMethod
    public Reservation getReservationById(@WebParam(name = "id") Long id) {
        Optional<Reservation> reservationOpt = reservationService.getReservationById(id);
        if (reservationOpt.isPresent()) {
            return reservationOpt.get();
        }
        throw new RuntimeException("Reservation not found with id: " + id);
    }

    @WebMethod
    public Reservation createReservation(
            @WebParam(name = "clientName") String clientName,@WebParam(name = "roomType") String roomType,@WebParam(name = "checkInDate") String checkInDate,@WebParam(name = "checkOutDate") String checkOutDate) {
        
        try {
            Reservation reservation = new Reservation();
            reservation.setClientName(clientName);
            reservation.setRoomType(roomType);
            reservation.setCheckInDate(parseDate(checkInDate));
            reservation.setCheckOutDate(parseDate(checkOutDate));

            return reservationService.createReservation(reservation);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing dates", e);
        }
    }

    @WebMethod
    public boolean deleteReservation(@WebParam(name = "id") Long id) {
        try {
            reservationService.deleteReservation(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not delete reservation with id: " + id, e);
        }
    }

   
    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("Invalid date format, should be yyyy-MM-dd", e);
        }
    }
}
