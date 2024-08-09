package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;
import com.Hayati.Reservation.des.Hotels.repositoriy.HotelRepositoriy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepositoriy hotelRepositoriy;

    public HotelDto createHotel(HotelDto hotelDto, String imageUrl) {
       
        Hotel hotel = new Hotel();
        hotel.setName(hotelDto.getName());
        hotel.setEmplacement(hotelDto.getEmplacement());
        hotel.setEvaluation(hotelDto.getEvaluation());
        hotel.setLocalisation(hotelDto.getLocalisation());
        hotel.setCommentaires(hotelDto.getCommentaires());
        hotel.setNotifications(hotelDto.getNotifications());
        hotel.setImageUrl(imageUrl);  

        Hotel savedHotel = hotelRepositoriy.save(hotel);

        
        HotelDto savedHotelDto = new HotelDto();
        savedHotelDto.setId_hot(savedHotel.getId_hot());
        savedHotelDto.setName(savedHotel.getName());
        savedHotelDto.setEmplacement(savedHotel.getEmplacement());
        savedHotelDto.setEvaluation(savedHotel.getEvaluation());
        savedHotelDto.setLocalisation(savedHotel.getLocalisation());
        savedHotelDto.setCommentaires(savedHotel.getCommentaires());
        savedHotelDto.setNotifications(savedHotel.getNotifications());
        savedHotelDto.setImageUrl(savedHotel.getImageUrl()); // Ensure imageUrl is included

        return savedHotelDto;
    }

    public List<HotelDto> getAllHotels() {
        return hotelRepositoriy.findAll().stream().map(hotel -> {
            HotelDto hotelDto = new HotelDto();
            hotelDto.setId_hot(hotel.getId_hot());
            hotelDto.setName(hotel.getName());
            hotelDto.setEmplacement(hotel.getEmplacement());
            hotelDto.setEvaluation(hotel.getEvaluation());
            hotelDto.setLocalisation(hotel.getLocalisation());
            hotelDto.setCommentaires(hotel.getCommentaires());
            hotelDto.setNotifications(hotel.getNotifications());
            hotelDto.setImageUrl(hotel.getImageUrl()); 
            return hotelDto;
        }).collect(Collectors.toList());
    }

    public HotelDto getHotelById(Long id) {
        return hotelRepositoriy.findById(id).map(hotel -> {
            HotelDto hotelDto = new HotelDto();
            hotelDto.setId_hot(hotel.getId_hot());
            hotelDto.setName(hotel.getName());
            hotelDto.setEmplacement(hotel.getEmplacement());
            hotelDto.setEvaluation(hotel.getEvaluation());
            hotelDto.setLocalisation(hotel.getLocalisation());
            hotelDto.setCommentaires(hotel.getCommentaires());
            hotelDto.setNotifications(hotel.getNotifications());
            hotelDto.setImageUrl(hotel.getImageUrl()); 
            return hotelDto;
        }).orElse(null);
    }

    public HotelDto updateHotel(Long id, HotelDto hotelDto) {
        return hotelRepositoriy.findById(id).map(hotel -> {
            hotel.setName(hotelDto.getName());
            hotel.setEmplacement(hotelDto.getEmplacement());
            hotel.setEvaluation(hotelDto.getEvaluation());
            hotel.setLocalisation(hotelDto.getLocalisation());
            hotel.setCommentaires(hotelDto.getCommentaires());
            hotel.setNotifications(hotelDto.getNotifications());
            Hotel updatedHotel = hotelRepositoriy.save(hotel);
            HotelDto updatedHotelDto = new HotelDto();
            updatedHotelDto.setId_hot(updatedHotel.getId_hot());
            updatedHotelDto.setName(updatedHotel.getName());
            updatedHotelDto.setEmplacement(updatedHotel.getEmplacement());
            updatedHotelDto.setEvaluation(updatedHotel.getEvaluation());
            updatedHotelDto.setLocalisation(updatedHotel.getLocalisation());
            updatedHotelDto.setCommentaires(updatedHotel.getCommentaires());
            updatedHotelDto.setNotifications(updatedHotel.getNotifications());
            updatedHotelDto.setImageUrl(updatedHotel.getImageUrl()); 
            return updatedHotelDto;
        }).orElse(null);
    }

    public void deleteHotel(Long id) {
        hotelRepositoriy.deleteById(id);
    }
}
