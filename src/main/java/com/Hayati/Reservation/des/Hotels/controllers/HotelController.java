package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.dto.ReservationDto;
import com.Hayati.Reservation.des.Hotels.services.HotelService;
import com.Hayati.Reservation.des.Hotels.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth/Hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto) {
        HotelDto createdHotel = hotelService.createHotel(hotelDto);
        return ResponseEntity.ok(createdHotel);
    }

    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        List<HotelDto> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        HotelDto hotel = hotelService.getHotelById(id);
        return hotel != null ? ResponseEntity.ok(hotel) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto) {
        HotelDto updatedHotel = hotelService.updateHotel(id, hotelDto);
        return updatedHotel != null ? ResponseEntity.ok(updatedHotel) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}
