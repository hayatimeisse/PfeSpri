package com.Hayati.Reservation.des.Hotels.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdateClientDto {
    private String name;
    private String email;
    private String password;
    private String numerodetelephone;
    private MultipartFile photo;


    public String getEmail() {
        return email;
    }

    public UpdateClientDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UpdateClientDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNom() {
        return name;
    }

    public UpdateClientDto setNom(String name) {
        this.name = name;
        return this;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public UpdateClientDto setPhoto(MultipartFile photo) {
        this.photo = photo;
        return this;
    }

    public String getNumerodetelephone() {
        return numerodetelephone;
    }

    public UpdateClientDto setNumerodetelephone(String numerodetelephone) {
        this.numerodetelephone = numerodetelephone;
        return this;
    }

   
}
