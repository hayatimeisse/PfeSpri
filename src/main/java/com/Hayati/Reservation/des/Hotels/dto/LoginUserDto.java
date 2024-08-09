package com.Hayati.Reservation.des.Hotels.dto;

public class LoginUserDto {
    private String emailOrnumerodetelephone;
    private String password;

   

    public String getEmailOrnumerodetelephone() {
        return emailOrnumerodetelephone;
    }

    public LoginUserDto setEmailOrnumerodetelephone(String emailOrnumerodetelephone) {
        this.emailOrnumerodetelephone = emailOrnumerodetelephone;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "LoginUserDto{" +
                "emailOrnumerodetelephone='" + emailOrnumerodetelephone + '\'' +
                ", password='" + password + '\'' +
                
                '}';
    }


}