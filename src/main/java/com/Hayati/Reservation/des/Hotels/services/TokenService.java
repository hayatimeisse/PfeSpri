package com.Hayati.Reservation.des.Hotels.services;

import org.springframework.stereotype.Service;

import com.Hayati.Reservation.des.Hotels.repositoriy.TokenRepository;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void invalidateTokens(Long userId) {
        tokenRepository.invalidateByUserId(userId);
    }
}
