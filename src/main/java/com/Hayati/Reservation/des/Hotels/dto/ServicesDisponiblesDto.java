package com.Hayati.Reservation.des.Hotels.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicesDisponiblesDto {
    private Long id_ser;
    private String type;

    // Constructor that accepts both id_ser and type
    public ServicesDisponiblesDto(Long id_ser, String type) {
        this.id_ser = id_ser;
        this.type = type;
    }

    // Default constructor (optional, but useful if you want to use object mapping libraries like ModelMapper)
    public ServicesDisponiblesDto() {}
}
