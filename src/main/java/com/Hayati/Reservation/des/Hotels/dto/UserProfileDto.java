package com.Hayati.Reservation.des.Hotels.dto;

import com.Hayati.Reservation.des.Hotels.entity.Subscribe;

public class UserProfileDto {
    private String name;
    private String numerodetelephone;
    private String email;
    private boolean enabled;
    private String photo;

    public UserProfileDto(Subscribe user) {
        this.name = user.getName();
        this.numerodetelephone = user.getNumerodetelephone();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        
        String baseUrl = "http://localhost:9001/subscribe_photos/";
        this.photo = baseUrl + user.getPhoto();

    }

    // Getters
    public String getName() {
        return name;
    }

    public String getNumerodetelephone() {
        return numerodetelephone;
    }

    

    public String getEmail() {
        return email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getPhoto() {
        return photo;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setNumerodetelephone(String numerodetelephone) {
        this.numerodetelephone = numerodetelephone;
    }

    

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

