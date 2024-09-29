package com.Hayati.Reservation.des.Hotels.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuiteDto {
    private Long id_sui;
    private float prixJour;
    private boolean disponibilites;
    private String description;
    private Long hotel_id;
    private String imageUrl;

    // Chained setter methods for method chaining
    public SuiteDto setId_sui(Long id_sui) {
        this.id_sui = id_sui;
        return this;
    }

    public SuiteDto setPrixJour(float prixJour) {
        this.prixJour = prixJour;
        return this;
    }

    public SuiteDto setDisponibilites(boolean disponibilites) {
        this.disponibilites = disponibilites;
        return this;
    }

    public SuiteDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public SuiteDto setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
        return this;
    }

    public SuiteDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
