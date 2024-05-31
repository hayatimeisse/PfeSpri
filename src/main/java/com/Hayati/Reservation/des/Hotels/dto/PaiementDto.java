package com.Hayati.Reservation.des.Hotels.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

   
public class PaiementDto {
    private Long id_pai;
    private float montant;
    private Date datepaiement;
    private String methodepaiement;
    
}
