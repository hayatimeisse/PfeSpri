package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.ChambreDto;
import com.Hayati.Reservation.des.Hotels.entity.Chambre;
import com.Hayati.Reservation.des.Hotels.entity.Suite;
import com.Hayati.Reservation.des.Hotels.repositoriy.ChambreRepositoriy;
import com.Hayati.Reservation.des.Hotels.repositoriy.SuiteRepositoriy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChambreService {

    @Autowired
    private ChambreRepositoriy chambreRepositoriy;

    @Autowired
    private SuiteRepositoriy suiteRepositoriy;

    @Autowired
    private ModelMapper modelMapper;

    public ChambreDto createChambre(ChambreDto chambreDto, String imageUrl) {
        Chambre chambre = modelMapper.map(chambreDto, Chambre.class);

        if (chambreDto.getSuite_id() != null) {
            Optional<Suite> suite = suiteRepositoriy.findById(chambreDto.getSuite_id());
            if (suite.isPresent()) {
                chambre.setSuite(suite.get());
            } else {
                throw new RuntimeException("suite not found with id: " + chambreDto.getSuite_id());
            }
        }

        chambre.setImageUrl(imageUrl); // Set the image URL
        chambre = chambreRepositoriy.save(chambre);

        ChambreDto resultDto = modelMapper.map(chambre, ChambreDto.class);
        resultDto.setSuite_id(chambre.getSuite() != null ? chambre.getSuite().getId_sui() : null);

        return resultDto;
    }

    public List<ChambreDto> getAllChambres() {
        return chambreRepositoriy.findAll()
                .stream()
                .map(chambre -> modelMapper.map(chambre, ChambreDto.class))
                .collect(Collectors.toList());
    }

    public ChambreDto getChambreById(Long id) {
        return chambreRepositoriy.findById(id)
                .map(chambre -> modelMapper.map(chambre, ChambreDto.class))
                .orElse(null);
    }

    public ChambreDto updateChambre(Long id, ChambreDto chambreDto, String imageUrl) {
        Optional<Chambre> optionalChambre = chambreRepositoriy.findById(id);
        if (optionalChambre.isPresent()) {
            Chambre chambre = optionalChambre.get();
            chambre.setCapacite(chambreDto.getCapacite());
            chambre.setPrixJour(chambreDto.getPrixJour());
            chambre.setDisponibilites(chambreDto.getDisponibilites());
            chambre.setDescription(chambreDto.getDescription());
            chambre.setImageUrl(imageUrl); // Update the image URL

            Optional<Suite> suite = suiteRepositoriy.findById(chambreDto.getSuite_id());
            suite.ifPresent(chambre::setSuite);

            chambre = chambreRepositoriy.save(chambre);

            return modelMapper.map(chambre, ChambreDto.class);
        }
        return null;
    }

    public void deleteChambre(Long id) {
        chambreRepositoriy.deleteById(id);
    }
}
