package com.Hayati.Reservation.des.Hotels.services;

import org.apache.commons.mail.EmailException;

public interface EmailService {

    void sendSimpleEmail(String to, String subject, String message) throws EmailException;
    
    // Overloaded method to handle cases where you only need 3 parameters
    void sendEmail(String to, String subject, String message) throws EmailException;

    // Original method that expects 4 parameters
    void sendEmail(String to, String subject, String fullName, String random) throws EmailException;
}
