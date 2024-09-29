package com.Hayati.Reservation.des.Hotels.services;

import java.util.Collections;

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

    public List<ServicesDisponiblesDto> getServicesByHotelId(Long hotelId) {
        List<ServicesDisponibles> services = servicesDisponiblesRepositoriy.findServicesByHotelId(hotelId);
    
        if (services != null && !services.isEmpty()) {
            return services.stream()
                    .map(service -> new ServicesDisponiblesDto(service.getId_ser(), service.getType()))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
    
    
    // Créer un service
    public ServicesDisponiblesDto createServicesDisponibles(ServicesDisponiblesDto servicesDisponiblesDto) {
        ServicesDisponibles servicesDisponibles = modelMapper.map(servicesDisponiblesDto, ServicesDisponibles.class);
        servicesDisponibles = servicesDisponiblesRepositoriy.save(servicesDisponibles);
        return modelMapper.map(servicesDisponibles, ServicesDisponiblesDto.class);
    }

    // Obtenir tous les services
    public List<ServicesDisponiblesDto> getAllServicesDisponibless() {
        return servicesDisponiblesRepositoriy.findAll()
                .stream()
                .map(servicesDisponibles -> modelMapper.map(servicesDisponibles, ServicesDisponiblesDto.class))
                .collect(Collectors.toList());
    }

    // Obtenir un service par son ID
    public ServicesDisponiblesDto getServicesDisponiblesById(Long id) {
        return servicesDisponiblesRepositoriy.findById(id)
                .map(servicesDisponibles -> modelMapper.map(servicesDisponibles, ServicesDisponiblesDto.class))
                .orElse(null);
    }

    // Mettre à jour un service
    public ServicesDisponiblesDto updateServicesDisponibles(Long id, ServicesDisponiblesDto servicesDisponiblesDto) {
        return servicesDisponiblesRepositoriy.findById(id)
                .map(existingServicesDisponibles -> {
                    existingServicesDisponibles.setType(servicesDisponiblesDto.getType());
                    servicesDisponiblesRepositoriy.save(existingServicesDisponibles);
                    return modelMapper.map(existingServicesDisponibles, ServicesDisponiblesDto.class);
                })
                .orElse(null);
    }

    // Supprimer un service
    public void deleteServicesDisponibles(Long id) {
        servicesDisponiblesRepositoriy.deleteById(id);
    }
}
