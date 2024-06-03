package com.Hayati.Reservation.des.Hotels.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hayati.Reservation.des.Hotels.dto.ServicesDisponiblesDto;
import com.Hayati.Reservation.des.Hotels.entity.ServicesDisponibles;
import com.Hayati.Reservation.des.Hotels.repositoriy.ServicesDisponiblesRepositoriy;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesDisponiblesService {

    @Autowired
    private ServicesDisponiblesRepositoriy servicesDisponiblesRepositoriy;

    @Autowired
    private ModelMapper modelMapper;

    public ServicesDisponiblesDto createServicesDisponibles(ServicesDisponiblesDto servicesDisponiblesDto) {
        ServicesDisponibles servicesDisponibles = modelMapper.map(servicesDisponiblesDto, ServicesDisponibles.class);
        servicesDisponibles = servicesDisponiblesRepositoriy.save(servicesDisponibles);
        return modelMapper.map(servicesDisponibles, ServicesDisponiblesDto.class);
    }

    public List<ServicesDisponiblesDto> getAllServicesDisponibless() {
        return servicesDisponiblesRepositoriy.findAll()
                .stream()
                .map(servicesDisponibles -> modelMapper.map(servicesDisponiblesRepositoriy, ServicesDisponiblesDto.class))
                .collect(Collectors.toList());
    }

    public ServicesDisponiblesDto getServicesDisponiblesById(Long id) {
        return servicesDisponiblesRepositoriy.findById(id)
                .map(servicesDisponibles -> modelMapper.map(servicesDisponiblesRepositoriy, ServicesDisponiblesDto.class))
                .orElse(null);
    }

    public ServicesDisponiblesDto updateServicesDisponibles(Long id, ServicesDisponiblesDto servicesDisponiblesDto) {
        return servicesDisponiblesRepositoriy.findById(id)
                .map(existingServicesDisponibles -> {
                    // Mise à jour des attributs de l'objet existingPaiement avec les valeurs de paiementDto
                    existingServicesDisponibles.setType(servicesDisponiblesDto.getType());
                   
                    // Enregistrement des modifications dans le repository
                    servicesDisponiblesRepositoriy.save(existingServicesDisponibles);
                    // Mapper l'objet existingPaiement en objet PaiementDto et le renvoyer
                    return modelMapper.map(existingServicesDisponibles, ServicesDisponiblesDto.class);
                })
                .orElse(null);
    }
    

    public void deleteServicesDisponibles(Long id) {
        servicesDisponiblesRepositoriy.deleteById(id);
    }
}

