package com.Hayati.Reservation.des.Hotels.services;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Hayati.Reservation.des.Hotels.dto.SuiteDto;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;
import com.Hayati.Reservation.des.Hotels.entity.Suite;
import com.Hayati.Reservation.des.Hotels.repositoriy.HotelRepositoriy;
import com.Hayati.Reservation.des.Hotels.repositoriy.SuiteRepositoriy;

@Service
public class SuiteService {
    @Autowired
    private SuiteRepositoriy suiteRepositoriy;

    @Autowired
    private HotelRepositoriy hotelRepositoriy;

    @Autowired
    private ModelMapper modelMapper;

    public SuiteDto createSuite(SuiteDto suiteDto) {
        Suite suite = modelMapper.map(suiteDto, Suite.class); // Correction ici

        if (suiteDto.getHotel_id() != null) {
            Optional<Hotel> hotel = hotelRepositoriy.findById(suiteDto.getHotel_id());
            if (hotel.isPresent()) {
                suite.setHotel(hotel.get());
            } else {
                throw new RuntimeException("Hotel not found with id: " + suiteDto.getHotel_id());
            }
        }

        suite = suiteRepositoriy.save(suite);

        SuiteDto resultDto = modelMapper.map(suite, SuiteDto.class);
        resultDto.setHotel_id(suite.getHotel() != null ? suite.getHotel().getId_hot() : null);

        return resultDto;
    }

    public List<SuiteDto> getAllSuites() {
        return suiteRepositoriy.findAll()
                .stream()
                .map(suite -> modelMapper.map(suite, SuiteDto.class))
                .collect(Collectors.toList());
    }

    public SuiteDto getSuiteById(Long id) {
        return suiteRepositoriy.findById(id)
                .map(suite -> modelMapper.map(suite, SuiteDto.class))
                .orElse(null);
    }

    public SuiteDto updateSuite(Long id, SuiteDto suiteDto) {
        Optional<Suite> optionalSuite = suiteRepositoriy.findById(id);
        if (optionalSuite.isPresent()) {
            Suite suite = optionalSuite.get();
            suite.setPrixJour(suiteDto.getPrixJour());
            suite.setDisponibilites(suiteDto.getDisponibilites());
            suite.setDescription(suiteDto.getDescription());
            suite.setPhotos(suiteDto.getPhotos());

            Optional<Hotel> hotel = hotelRepositoriy.findById(suiteDto.getHotel_id());
            hotel.ifPresent(suite::setHotel);

            suite = suiteRepositoriy.save(suite);

            return modelMapper.map(suite, SuiteDto.class);
        }
        return null;
    }

    public void deleteSuite(Long id) {
        suiteRepositoriy.deleteById(id);
    }
}
