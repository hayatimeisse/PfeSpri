package com.Hayati.Reservation.des.Hotels.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuiteDto {
    private Long id_sui;
    private Long hotel_id;
    private float prixJour;
    private String disponibilites;
    private String description;
    private String imageUrl; 
}
