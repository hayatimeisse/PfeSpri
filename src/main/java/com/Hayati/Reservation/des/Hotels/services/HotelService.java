package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.ChambreDto;
import com.Hayati.Reservation.des.Hotels.dto.EmployeeDto;
import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.entity.Chambre;
import com.Hayati.Reservation.des.Hotels.entity.Employee;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;
import com.Hayati.Reservation.des.Hotels.repositoriy.ChambreRepositoriy;
import com.Hayati.Reservation.des.Hotels.repositoriy.EmployeeRepositoriy;
import com.Hayati.Reservation.des.Hotels.repositoriy.HotelRepositoriy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepositoriy hotelRepositoriy;

    @Autowired
    private ModelMapper modelMapper;

    public HotelDto createHotel(HotelDto hotelDto) {
        if (hotelDto.getImageUrl() == null || hotelDto.getImageUrl().isEmpty()) {
            hotelDto.setImageUrl("default-image-url");
        }
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel = hotelRepositoriy.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    public List<HotelDto> getAllHotels() {
        return hotelRepositoriy.findAll()
                .stream()
                .map(hotel -> modelMapper.map(hotel, HotelDto.class))
                .collect(Collectors.toList());
    }

    public HotelDto getHotelById(Long id) {
        return hotelRepositoriy.findById(id)
                .map(hotel -> modelMapper.map(hotel, HotelDto.class))
                .orElse(null);
    }

    public HotelDto updateHotel(Long id, HotelDto hotelDto) {
        return hotelRepositoriy.findById(id)
                .map(existingHotel -> {
                    existingHotel.setName(hotelDto.getName());
                    existingHotel.setEmplacement(hotelDto.getEmplacement());
                    existingHotel.setEvaluation(hotelDto.getEvaluation());
                    existingHotel.setLocalisation(hotelDto.getLocalisation());
                    existingHotel.setCommentaires(hotelDto.getCommentaires());
                    existingHotel.setNotifications(hotelDto.getNotifications());
                    existingHotel.setImageUrl(hotelDto.getImageUrl());

                    hotelRepositoriy.save(existingHotel);
                    return modelMapper.map(existingHotel, HotelDto.class);
                })
                .orElse(null);
    }

    public void deleteHotel(Long id) {
        hotelRepositoriy.deleteById(id);
    }
}

 