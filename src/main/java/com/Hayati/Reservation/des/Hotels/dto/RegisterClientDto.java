package com.Hayati.Reservation.des.Hotels.dto;

import org.springframework.web.multipart.MultipartFile;

public class RegisterClientDto {
    private String email;
    private String password;
    private String nom;
    private MultipartFile photo;
    private String numerodetelephone;
    private boolean isEmailVerified = true; 
    // Getters and setters for all fields
    public String getEmail() {
        return email;
    }

    public RegisterClientDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterClientDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNom() {
        return nom;
    }

    public RegisterClientDto setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public RegisterClientDto setPhoto(MultipartFile photo) {
        this.photo = photo;
        return this;
    }

    public String getNumerodetelephone() {
        return numerodetelephone;
    }

    public RegisterClientDto setNumerodetelephone(String numerodetelephone) {
        this.numerodetelephone = numerodetelephone;
        return this;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public RegisterClientDto setEmailVerified(boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
        return this;
    }
}
