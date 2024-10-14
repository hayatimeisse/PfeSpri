package com.Hayati.Reservation.des.Hotels.dto;

import org.springframework.web.multipart.MultipartFile;

public class RegisterSubscribeDto {
    private String email;
    private String password;
    private String nom;
    private MultipartFile photo;

    public MultipartFile getPhoto() {
        return photo;
    }

    public RegisterSubscribeDto setPhoto(MultipartFile photo) {
        this.photo = photo;
        return this;
    }
    public String getEmail() {
        return email;
    }

    public RegisterSubscribeDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterSubscribeDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNom() {
        return nom;
    }

    public RegisterSubscribeDto setNom(String nom) {
        this.nom = nom;
        return this;
    }

}
