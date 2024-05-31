package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.ReservationDto;
import com.Hayati.Reservation.des.Hotels.entity.Reservation;
import com.Hayati.Reservation.des.Hotels.repositoriy.PaiementRepositoriy;
import com.Hayati.Reservation.des.Hotels.repositoriy.ReservationRepositoriy;
import com.Hayati.Reservation.des.Hotels.entity.Paiement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ReservationService {

    @Autowired
    private ReservationRepositoriy reservationRepositoriy;

    @Autowired
    private PaiementRepositoriy paiementRepositoriy;

    @Autowired
    private ModelMapper modelMapper;

    public ReservationDto createReservation(ReservationDto reservationDto) {
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
        
        // Gestion du paiement
        if (reservationDto.getPaiement_id() != null) {
            Optional<Paiement> paiement = paiementRepositoriy.findById(reservationDto.getPaiement_id());
            if (paiement.isPresent()) {
                reservation.setPaiement(paiement.get());
            } else {
                // Si aucun paiement n'est trouvé avec l'id fourni, traiter selon les besoins (par exemple, renvoyer une erreur)
                // throw new RuntimeException("Paiement not found with id: " + reservationDto.getPaiement_id());
            }
        }
        
        // Enregistrement de la réservation avec le paiement lié
        reservation = reservationRepositoriy.save(reservation);
        
        // Assurez-vous que le DTO retourné a l'id du paiement correctement configuré
        ReservationDto resultDto = modelMapper.map(reservation, ReservationDto.class);
        resultDto.setPaiement_id(reservation.getPaiement() != null ? reservation.getPaiement().getId_pai() : null);
        
        return resultDto;
    }
    

    public List<ReservationDto> getAllReservations() {
        return reservationRepositoriy.findAll()
                .stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDto.class))
                .collect(Collectors.toList());
    }
    

    public ReservationDto getReservationById(Long id) {
        return reservationRepositoriy.findById(id)
                .map(reservation -> modelMapper.map(reservation, ReservationDto.class))
                .orElse(null);
    }

  
    public ReservationDto updatereservation(Long id, ReservationDto reservationDto) {
        Optional<Reservation> optionalReservation = reservationRepositoriy.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setDateSejour(reservationDto.getDateSejour());
            reservation.setNombreChambre(reservationDto.getNombreChambre());
            reservation.setStatutReser(reservationDto.getStatutReser());
            reservation.setMontantTotal(reservationDto.getMontantTotal());
            reservation.setDatefin(reservationDto.getDatefin());
          

            // Fetch and set the role
            Optional<Paiement> paiement = paiementRepositoriy.findById(reservationDto.getPaiement_id());
            paiement.ifPresent(reservation::setPaiement);

            reservation = reservationRepositoriy.save(reservation);
            return modelMapper.map(reservation, ReservationDto.class);
        }
        return null;
    }

    public void deleteReservation(Long id) {
        reservationRepositoriy.deleteById(id);
    }
}
