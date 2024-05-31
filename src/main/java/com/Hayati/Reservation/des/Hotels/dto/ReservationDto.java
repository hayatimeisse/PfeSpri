package com.Hayati.Reservation.des.Hotels.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDto {
    private Long id_res;
    private Long paiement_id;
    private Date dateSejour;
    private float nombreChambre;
    private String statutReser;
    private float montantTotal;
    private Date datefin;
}