package com.Hayati.Reservation.des.Hotels.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hayati.Reservation.des.Hotels.dto.CardInfoDTO;
import com.Hayati.Reservation.des.Hotels.entity.CardInfo;
import com.Hayati.Reservation.des.Hotels.repositoriy.CardInfoRepository;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Service
public class CardInfoService {

    @Autowired
    private CardInfoRepository cardInfoRepository;

    public CardInfo saveCardInfo(CardInfoDTO cardInfoDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth expiration = YearMonth.parse(cardInfoDTO.getExpirationDate(), formatter);

        CardInfo cardInfo = CardInfo.builder()
                .cardNumber(cardInfoDTO.getCardNumber())
                .cardholderName(cardInfoDTO.getCardholderName())
                .expirationDate(expiration)
                .cvc(cardInfoDTO.getCvc())
                .country(cardInfoDTO.getCountry())
                .build();

        return cardInfoRepository.save(cardInfo);
    }
}
