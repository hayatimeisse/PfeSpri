package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.BankilyAuthResponse;

public interface BankilyAuthService {
    BankilyAuthResponse authenticate();
    BankilyAuthResponse refreshToken(String refreshToken);
}
