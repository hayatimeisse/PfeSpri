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

        // // Ensure the photo URL is correct
        // if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
        //     if (user.getPhoto().startsWith("http://") || user.getPhoto().startsWith("https://")) {
        //         this.photo = user.getPhoto(); // Use the existing full URL
        //     } else {
        //         String baseUrl = "http://localhost:9001/subscribe_photos/";
        //         this.photo = baseUrl + user.getPhoto(); // Generate the full URL
        //     }
        // } else {
        //     this.photo = null; // No photo available
        // }
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumerodetelephone() {
        return numerodetelephone;
    }

    public void setNumerodetelephone(String numerodetelephone) {
        this.numerodetelephone = numerodetelephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
