package com.Hayati.Reservation.des.Hotels.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.Hayati.Reservation.des.Hotels.dto.CardInfoDTO;
import com.Hayati.Reservation.des.Hotels.entity.CardInfo;
import com.Hayati.Reservation.des.Hotels.services.CardInfoService;

@RestController
@RequestMapping("/auth/card-info")
@Validated
public class CardInfoController {

    @Autowired
    private CardInfoService cardInfoService;

    @PostMapping
    public ResponseEntity<CardInfo> saveCardInfo(@Valid @RequestBody CardInfoDTO cardInfoDTO) {
        CardInfo savedCardInfo = cardInfoService.saveCardInfo(cardInfoDTO);
        return ResponseEntity.ok(savedCardInfo);
    }
}
