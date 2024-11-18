package com.Hayati.Reservation.des.Hotels.dto;

import com.Hayati.Reservation.des.Hotels.enumeration.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeDto {
    private String name;
    private String email;
    private String password;
       private String imageUrl;
    private Status status; 
    
}
