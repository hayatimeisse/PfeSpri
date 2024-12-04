package com.Hayati.Reservation.des.Hotels.dto;

import com.Hayati.Reservation.des.Hotels.entity.Client;


public class UserProfileClientDto {
    private String name;
    private String email;

    public UserProfileClientDto(Client user) {
        this.name = user.getName();
        this.email = user.getEmail();

       
    }

    // Getters et Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   
}
