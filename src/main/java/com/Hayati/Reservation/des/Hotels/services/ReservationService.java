// package com.Hayati.Reservation.des.Hotels.services;

// import com.Hayati.Reservation.des.Hotels.dto.ReservationDto;
// import com.Hayati.Reservation.des.Hotels.entity.Reservation;
// import com.Hayati.Reservation.des.Hotels.entity.User;
// import com.Hayati.Reservation.des.Hotels.repositoriy.PaiementRepositoriy;
// import com.Hayati.Reservation.des.Hotels.repositoriy.ReservationRepositoriy;
// import com.Hayati.Reservation.des.Hotels.repositoriy.UserRepository;
// import com.Hayati.Reservation.des.Hotels.entity.Paiement;

// import org.modelmapper.ModelMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class ReservationService {

//     @Autowired
//     private ReservationRepositoriy reservationRepositoriy;

//     @Autowired
//     private PaiementRepositoriy paiementRepositoriy;
    
//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private ModelMapper modelMapper;

//     public ReservationDto createReservation(ReservationDto reservationDto) {
//         Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
        
//         // Handle Paiement
//         if (reservationDto.getPaiement_id() != null) {
//             Optional<Paiement> paiement = paiementRepositoriy.findById(reservationDto.getPaiement_id());
//             if (paiement.isPresent()) {
//                 reservation.setPaiement(paiement.get());
//             } else {
//                 // Handle case when no paiement is found
//                 throw new RuntimeException("Paiement not found with id: " + reservationDto.getPaiement_id());
//             }
//         }
    
//         // Handle User
//         if (reservationDto.getUser_id() != null) {
//             Optional<User> user = userRepository.findById(reservationDto.getUser_id());
//             if (user.isPresent()) {
//                 reservation.setUser(user.get());
//             } else {
//                 // Handle case when no user is found
//                 throw new RuntimeException("User not found with id: " + reservationDto.getUser_id());
//             }
//         }
    
//         // Save the reservation
//         reservation = reservationRepositoriy.save(reservation);
    
//         // Ensure the returned DTO has the correct ids set
//         ReservationDto resultDto = modelMapper.map(reservation, ReservationDto.class);
//         resultDto.setPaiement_id(reservation.getPaiement() != null ? reservation.getPaiement().getId_pai() : null);
//         resultDto.setUser_id(reservation.getUser() != null ? reservation.getUser().getId_user() : null);
    
//         return resultDto;
//     }
    
//     public List<ReservationDto> getAllReservations() {
//         return reservationRepository.findAll()
//                 .stream()
//                 .map(reservation -> modelMapper.map(reservation, ReservationDto.class))
//                 .collect(Collectors.toList());
//     }
    
//     public ReservationDto getReservationById(Long id) {
//         return reservationRepository.findById(id)
//                 .map(reservation -> modelMapper.map(reservation, ReservationDto.class))
//                 .orElse(null);
//     }

//     public ReservationDto updateReservation(Long id, ReservationDto reservationDto) {
//         Optional<Reservation> optionalReservation = reservationRepository.findById(id);
//         if (optionalReservation.isPresent()) {
//             Reservation reservation = optionalReservation.get();
//             reservation.setDateSejour(reservationDto.getDateSejour());
//             reservation.setNombreChambre(reservationDto.getNombreChambre());
//             reservation.setStatutReser(reservationDto.getStatutReser());
//             reservation.setMontantTotal(reservationDto.getMontantTotal());
//             reservation.setDatefin(reservationDto.getDatefin());
          
//             // Fetch and set the paiement
//             Optional<Paiement> paiement = paiementRepository.findById(reservationDto.getPaiement_id());
//             paiement.ifPresent(reservation::setPaiement);

//             reservation = reservationRepository.save(reservation);
//             return modelMapper.map(reservation, ReservationDto.class);
//         }
//         return null;
//     }

//     public void deleteReservation(Long id) {
//         reservationRepository.deleteById(id);
//     }
// }
