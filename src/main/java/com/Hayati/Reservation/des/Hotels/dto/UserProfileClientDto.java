package com.Hayati.Reservation.des.Hotels.dto;

import com.Hayati.Reservation.des.Hotels.entity.Client;


public class UserProfileClientDto {
    private String name;
    private String email;
    private String photo;
    private String numerodetelephone;

    public UserProfileClientDto(Client user) {
        this.name = user.getName();
        this.email = user.getEmail();

        // Vérifiez si la photo est déjà une URL complète
        if (user.getPhoto() != null && (user.getPhoto().startsWith("http://") || user.getPhoto().startsWith("https://"))) {
            this.photo = user.getPhoto(); // Utilisez l'URL existante
        } else if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
            this.photo = "http://localhost:9001/client_photos/" + user.getPhoto(); // Générer une nouvelle URL
        } else {
            this.photo = null; // Aucun fichier photo
        }

        this.numerodetelephone = user.getNumerodetelephone();
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNumerodetelephone() {
        return numerodetelephone;
    }

    public void setNumerodetelephone(String numerodetelephone) {
        this.numerodetelephone = numerodetelephone;
    }
}
