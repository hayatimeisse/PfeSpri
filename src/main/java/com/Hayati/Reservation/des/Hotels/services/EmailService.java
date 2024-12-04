package com.Hayati.Reservation.des.Hotels.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

import com.Hayati.Reservation.des.Hotels.entity.Admin;
import com.Hayati.Reservation.des.Hotels.entity.Client;
import com.Hayati.Reservation.des.Hotels.entity.ResetCode;
import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.repositoriy.AdminRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.ClientRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.ResetCodeRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.SubscribeRepository;

import io.swagger.v3.core.util.Json;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private SubscribeRepository subscribeRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
private AdminRepository adminRepository;
    @Autowired
    private ResetCodeRepository resetCodeRepository;
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    public void sendVerificationEmail(Subscribe user) {
        String verificationCode = generateVerificationCode();
Json.pretty(user);
        System.out.println(verificationCode);
        user.setVerificationCode(verificationCode); 
        subscribeRepository.save(user); 
    
        String activationLink = "http://localhost:9001/api/auth/verify-email?userId="
                + user.getId() + "&code=" + verificationCode;
    
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Vérifiez votre email");
        message.setText("Cliquez sur ce lien pour vérifier votre email : " + activationLink);
    
        mailSender.send(message);
    }
    public void sendVerificationEmail(Admin admin) {
        String verificationCode = generateVerificationCode();
        
        System.out.println(verificationCode);
        
        admin.setVerificationCode(verificationCode);
        
        adminRepository.save(admin);
        
        String activationLink = "http://localhost:9001/api/auth/verify-email?adminId="
                + admin.getId() + "&code=" + verificationCode;
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(admin.getEmail());
        message.setSubject("Vérifiez votre email");
        message.setText("Cliquez sur ce lien pour vérifier votre email : " + activationLink);
        
        mailSender.send(message);
    }
    public void sendVerificationEmail(Client client) {
        String verificationCode = generateVerificationCode();
        
        System.out.println(verificationCode);
        
        client.setVerificationCode(verificationCode);
        
        clientRepository.save(client);
        
        String activationLink = "http://localhost:9001/api/auth/verify-email?clientId="
                + client.getId() + "&code=" + verificationCode;
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(client.getEmail());
        message.setSubject("Vérifiez votre email");
        message.setText("Cliquez sur ce lien pour vérifier votre email : " + activationLink);
        
        mailSender.send(message);
    }
    
    public void sendPasswordResetCode(String email) {
        String resetCode = generateResetCode();
        resetCodeRepository.save(new ResetCode(email, resetCode));

        String subject = "Password Reset Request";
        String message = "Here is your password reset code: " + resetCode;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    private String generateResetCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }
    
    private String generateVerificationCode() {
        Random random = new Random();
        return String.valueOf(100000 + random.nextInt(900000)); 
    }
   

}
