package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.entity.Chambre;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;
import com.Hayati.Reservation.des.Hotels.repositoriy.ChambreRepositoriy;
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
    private ChambreRepositoriy chambreRepositoriy;

    @Autowired
    private ModelMapper modelMapper;

    public HotelDto createHotel(HotelDto hotelDto) {
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);

        // Gestion de la chambre
        if (hotelDto.getChambre_id() != null) {
            Optional<Chambre> chambre = chambreRepositoriy.findById(hotelDto.getChambre_id());
            if (chambre.isPresent()) {
                hotel.setChambre(chambre.get());
            } else {
                // Si aucune chambre n'est trouvée avec l'id fourni, traiter selon les besoins (par exemple, renvoyer une erreur)
                throw new RuntimeException("Chambre not found with id: " + hotelDto.getChambre_id());
            }
        }

        // Enregistrement de l'hôtel avec la chambre liée
        hotel = hotelRepositoriy.save(hotel);

        // Assurez-vous que le DTO retourné a l'id de la chambre correctement configuré
        HotelDto resultDto = modelMapper.map(hotel, HotelDto.class);
        resultDto.setChambre_id(hotel.getChambre() != null ? hotel.getChambre().getId_cham() : null);

        return resultDto;
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
        Optional<Hotel> optionalHotel = hotelRepositoriy.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            hotel.setName(hotelDto.getName());
            hotel.setEmplacement(hotelDto.getEmplacement());
            hotel.setEvaluation(hotelDto.getEvaluation());
            hotel.setLocalisation(hotelDto.getLocalisation());
            hotel.setCommentaires(hotelDto.getCommentaires());
            hotel.setNotifications(hotelDto.getNotifications());

            // Fetch and set the chambre
            Optional<Chambre> chambre = chambreRepositoriy.findById(hotelDto.getChambre_id());
            chambre.ifPresent(hotel::setChambre);

            hotel = hotelRepositoriy.save(hotel);
            return modelMapper.map(hotel, HotelDto.class);
        }
        return null;
    }

    public void deleteHotel(Long id) {
        hotelRepositoriy.deleteById(id);
    }
}
