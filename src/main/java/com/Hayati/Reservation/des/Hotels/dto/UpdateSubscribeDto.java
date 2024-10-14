package com.Hayati.Reservation.des.Hotels.dto;

import org.springframework.web.multipart.MultipartFile;

public class UpdateSubscribeDto {
     private String name;
    private String email;
    private String password;
    private MultipartFile photo;


    public String getEmail() {
        return email;
    }

    public UpdateSubscribeDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UpdateSubscribeDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNom() {
        return name;
    }

    public UpdateSubscribeDto setNom(String name) {
        this.name = name;
        return this;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public UpdateSubscribeDto setPhoto(MultipartFile photo) {
        this.photo = photo;
        return this;
    }

}
