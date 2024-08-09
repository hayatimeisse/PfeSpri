package com.Hayati.Reservation.des.Hotels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SedadAndBimBankPaymentRequest {
    private String id_facture;
    private String id_transaction;
    private String  date_paiement;
    private double  montant;
    private String telephone_commercant;
    private String numero_recu;
    private String   note;
}
