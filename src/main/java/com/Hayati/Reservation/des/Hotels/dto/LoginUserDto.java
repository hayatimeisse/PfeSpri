package com.Hayati.Reservation.des.Hotels.dto;

public class LoginUserDto {
    private String emailOrnumerodetelephone; // Peut être soit un email soit un numéro de téléphone
    private String password;

    // Getters et Setters
    public String getEmailOrnumerodetelephone() {
        return emailOrnumerodetelephone;
    }

    public void setEmailOrnumerodetelephone(String emailOrnumerodetelephone) {
        this.emailOrnumerodetelephone = emailOrnumerodetelephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}