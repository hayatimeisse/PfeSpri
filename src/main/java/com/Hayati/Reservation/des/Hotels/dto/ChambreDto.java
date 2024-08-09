package com.Hayati.Reservation.des.Hotels.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChambreDto {
    private Long id_cham;
    private Long suite_id;
    private int capacite;
    private float prixJour;
    private String disponibilites;
    private String description;
    private String imageUrl; 
}
