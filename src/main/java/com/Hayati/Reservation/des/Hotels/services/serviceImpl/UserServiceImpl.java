// package com.Hayati.Reservation.des.Hotels.services.serviceImpl;


// import lombok.RequiredArgsConstructor;

// import org.springframework.stereotype.Service;

// import com.Hayati.Reservation.des.Hotels.entity.Confirmation;
// import com.Hayati.Reservation.des.Hotels.entity.User;
// import com.Hayati.Reservation.des.Hotels.repositoriy.ConfirmationRepository;
// import com.Hayati.Reservation.des.Hotels.repositoriy.UserRepository;
// import com.Hayati.Reservation.des.Hotels.services.EmailServic;
// import com.Hayati.Reservation.des.Hotels.services.UserService;

// /**
//  * @author Junior RT
//  * @version 1.0
//  * @license Get Arrays, LLC (https://getarrays.io)
//  * @since 6/24/2023
//  */

// @Service
// @RequiredArgsConstructor
// public class UserServiceImpl implements UserService {
//     private final UserRepository userRepository;
//     private final ConfirmationRepository confirmationRepository;
//     private final EmailServic emailServic;

//     @Override
//     public User saveUser(User user) {
//         if (userRepository.existsByEmail(user.getEmail())) { throw new RuntimeException("Email already exists"); }
//         user.setEnabled(false);
//         userRepository.save(user);

//         Confirmation confirmation = new Confirmation(user);
//         confirmationRepository.save(confirmation);

//         /* TODO Send email to user with token */
//         //emailService.sendSimpleMailMessage(user.getName(), user.getEmail(), confirmation.getToken());
//         //emailService.sendMimeMessageWithAttachments(user.getName(), user.getEmail(), confirmation.getToken());
//         //emailService.sendMimeMessageWithEmbeddedFiles(user.getName(), user.getEmail(), confirmation.getToken());
//         //emailService.sendHtmlEmail(user.getName(), user.getEmail(), confirmation.getToken());
//         emailServic.sendHtmlEmailWithEmbeddedFiles(user.getName(), user.getEmail(), confirmation.getToken());

//         return user;
//     }

//     @Override
//     public Boolean verifyToken(String token) {
//         Confirmation confirmation = confirmationRepository.findByToken(token);
//         User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
//         user.setEnabled(true);
//         userRepository.save(user);
//         //confirmationRepository.delete(confirmation);
//         return Boolean.TRUE;
//     }
// }