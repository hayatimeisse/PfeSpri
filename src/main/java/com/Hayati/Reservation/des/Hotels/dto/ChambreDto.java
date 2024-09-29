package com.Hayati.Reservation.des.Hotels.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChambreDto {
    private Long id_cham;
    private String name;
    private Long suite_id;
    private int capacite;
    private float prixJour;
    private boolean disponibilites;
    private String description;
    private String imageUrl;

 


    public ChambreDto setId_cham(Long id_cham) {
        this.id_cham = id_cham;
        return this;
    }

    public ChambreDto setName(String name) {
        this.name = name;
        return this;
    }

    public ChambreDto setSuite_id(Long suite_id) {
        this.suite_id = suite_id;
        return this;
    }

    public ChambreDto setCapacite(int capacite) {
        this.capacite = capacite;
        return this;
    }

    public ChambreDto setPrixJour(float prixJour) {
        this.prixJour = prixJour;
        return this;
    }

    public ChambreDto setDisponibilites(boolean disponibilites) {
        this.disponibilites = disponibilites;
        return this;
    }

    public ChambreDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public ChambreDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
