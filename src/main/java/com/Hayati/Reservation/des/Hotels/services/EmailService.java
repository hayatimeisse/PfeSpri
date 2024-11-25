package com.Hayati.Reservation.des.Hotels.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.repositoriy.SubscribeRepository;

import io.swagger.v3.core.util.Json;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SubscribeRepository subscribeRepository;

    public void sendVerificationEmail(Subscribe user) {
        String verificationCode = generateVerificationCode();
Json.pretty(user);
        System.out.println(verificationCode);
        user.setVerificationCode(verificationCode); // Définir le code dans l'utilisateur
        subscribeRepository.save(user); // Sauvegarder dans la base de données
    
        String activationLink = "http://localhost:9001/api/auth/verify-email?userId="
                + user.getId() + "&code=" + verificationCode;
    
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Vérifiez votre email");
        message.setText("Cliquez sur ce lien pour vérifier votre email : " + activationLink);
    
        mailSender.send(message);
    }
    
    private String generateVerificationCode() {
        Random random = new Random();
        return String.valueOf(100000 + random.nextInt(900000)); // Code à 6 chiffres
    }
   

}
