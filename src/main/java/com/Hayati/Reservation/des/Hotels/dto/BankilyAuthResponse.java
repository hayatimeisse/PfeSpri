package com.Hayati.Reservation.des.Hotels.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankilyAuthResponse {
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String refresh_expires_in;
}
