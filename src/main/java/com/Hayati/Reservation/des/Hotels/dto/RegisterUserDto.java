package com.Hayati.Reservation.des.Hotels.dto;

public class RegisterUserDto {
    private String email;
    private String password;
    private String name;
    private String numerodetelephone;
   
    public String getNumerodetelephone() {
        return numerodetelephone;
    }

    public RegisterUserDto setNumerodetelephone(String numerodetelephone) {
        this.numerodetelephone = numerodetelephone;
        return this;
    }
    public String getEmail() {
        return email;
    }

    public RegisterUserDto setEmail(String email) {
        this.email = email;
        return this;
    }


   
    public String getPassword() {
        return password;
    }

    public RegisterUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    // Getter and Setter for fullName
    public String getName() {
        return name;
    }

    public RegisterUserDto setName(String name) {
        this.name = name;
        return this;
    }

    
    
    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", numerodetelephone='" + numerodetelephone + '\'' +
                
                '}';
    }
}