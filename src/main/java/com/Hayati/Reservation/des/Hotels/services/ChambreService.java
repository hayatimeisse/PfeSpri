package com.Hayati.Reservation.des.Hotels.services;



import com.Hayati.Reservation.des.Hotels.dto.ChambreDto;
import com.Hayati.Reservation.des.Hotels.dto.PaiementDto;
import com.Hayati.Reservation.des.Hotels.entity.Chambre;
import com.Hayati.Reservation.des.Hotels.entity.Paiement;
import com.Hayati.Reservation.des.Hotels.repositoriy.ChambreRepositoriy;
import com.Hayati.Reservation.des.Hotels.repositoriy.PaiementRepositoriy;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChambreService {

    @Autowired
    private ChambreRepositoriy chambreRepositoriy;

    @Autowired
    private ModelMapper modelMapper;

    public ChambreDto createChambre(ChambreDto chambreDto) {
        Chambre chambre = modelMapper.map(chambreDto, Chambre.class);
        chambre = chambreRepositoriy.save(chambre);
        return modelMapper.map(chambre, ChambreDto.class);
    }

    public List<ChambreDto> getAllChambres() {
        return chambreRepositoriy.findAll()
                .stream()
                .map(role -> modelMapper.map(chambreRepositoriy, ChambreDto.class))
                .collect(Collectors.toList());
    }

    public ChambreDto getChambreById(Long id) {
        return chambreRepositoriy.findById(id)
                .map(role -> modelMapper.map(chambreRepositoriy, ChambreDto.class))
                .orElse(null);
    }

    public ChambreDto updateChambre(Long id, ChambreDto chambreDto) {
        return chambreRepositoriy.findById(id)
                .map(existingChambre -> {
                   
                    existingChambre.setCapacite(chambreDto.getCapacite());
                    existingChambre.setPrixJour(chambreDto.getPrixJour());
                    existingChambre.setDisponibilites(chambreDto.getDisponibilites());
                    existingChambre.setDescription(chambreDto.getDescription());
                    existingChambre.setPhotos(chambreDto.getPhotos());

                    chambreRepositoriy.save(existingChambre);
                    return modelMapper.map(existingChambre, ChambreDto.class);
                })
                .orElse(null);
    }
    

    public void deleteChambre(Long id) {
        chambreRepositoriy.deleteById(id);
    }
}
