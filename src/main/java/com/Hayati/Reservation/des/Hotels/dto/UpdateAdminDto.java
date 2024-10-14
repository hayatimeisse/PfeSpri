package com.Hayati.Reservation.des.Hotels.dto;

public class UpdateAdminDto {
    private String email;
    private String password;
    private String nom;

    private Boolean active;

    public Boolean getActive() {
        return active;
    }
    public UpdateAdminDto setActive(Boolean active) {
        this.active = active;
        return this;
    }


    // Getter and Setter for email
    public String getEmail() {
        return email;
    }
    public UpdateAdminDto setEmail(String email) {
        this.email = email;
        return this;
    }
    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public UpdateAdminDto setPassword(String password) {
        this.password = password;
        return this;
    }


    public String getNom() {
        return nom;
    }

    public UpdateAdminDto setNom(String nom) {
        this.nom = nom;
        return this;

    }
}
