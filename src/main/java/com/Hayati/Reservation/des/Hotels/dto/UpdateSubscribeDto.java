package com.Hayati.Reservation.des.Hotels.dto;

import org.springframework.web.multipart.MultipartFile;

import com.Hayati.Reservation.des.Hotels.enumeration.Status;

import jakarta.validation.constraints.NotBlank;

public class UpdateSubscribeDto {
    private String email;
    private String password;
    private String nom;
    private Status status;
    private MultipartFile photo;

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public UpdateSubscribeDto setEmail(String email) {
        this.email = email;
        return this; // Return the current instance
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public UpdateSubscribeDto setPassword(String password) {
        this.password = password;
        return this; // Return the current instance
    }

    // Getter and Setter for nom
    public String getNom() {
        return nom;
    }

    public UpdateSubscribeDto setNom(String nom) {
        this.nom = nom;
        return this; // Return the current instance
    }

    // Getter and Setter for status
    public Status getStatus() {
        return status;
    }

    public UpdateSubscribeDto setStatus(Status status) {
        this.status = status;
        return this; // Return the current instance
    }

    // Getter and Setter for photo
    public MultipartFile getPhoto() {
        return photo;
    }

    public UpdateSubscribeDto setPhoto(MultipartFile photo) {
        this.photo = photo;
        return this; // Return the current instance
    }
}

