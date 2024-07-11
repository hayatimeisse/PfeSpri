package com.Hayati.Reservation.des.Hotels.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HotelDto {
    private Long id_hot;
    private String name;
    private String emplacement;
    private String evaluation;
    private String localisation;
    private String commentaires;
    private String notifications;
    private String imageUrl;
}
