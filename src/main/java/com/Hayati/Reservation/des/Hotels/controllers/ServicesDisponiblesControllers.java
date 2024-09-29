package com.Hayati.Reservation.des.Hotels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.Hayati.Reservation.des.Hotels.dto.ServicesDisponiblesDto;
import com.Hayati.Reservation.des.Hotels.services.ServicesDisponiblesService;

@RestController
@RequestMapping("/api/auth/services")
public class ServicesDisponiblesControllers {

    @Autowired
    private ServicesDisponiblesService servicesDisponiblesService;

    // Create service
    @PostMapping
    public ResponseEntity<ServicesDisponiblesDto> createServicesDisponibles(@RequestBody ServicesDisponiblesDto servicesDisponiblesDto) {
        ServicesDisponiblesDto createdServicesDisponibles = servicesDisponiblesService.createServicesDisponibles(servicesDisponiblesDto);
        return ResponseEntity.ok(createdServicesDisponibles);
    }

    // Get all services
    @GetMapping
    public ResponseEntity<List<ServicesDisponiblesDto>> getAllServicesDisponibless() {
        List<ServicesDisponiblesDto> servicesDisponibless = servicesDisponiblesService.getAllServicesDisponibless();
        return ResponseEntity.ok(servicesDisponibless);
    }

    // Get services for a specific hotel by hotel ID
    
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<ServicesDisponiblesDto>> getHotelServices(@PathVariable Long hotelId) {
        List<ServicesDisponiblesDto> services = servicesDisponiblesService.getServicesByHotelId(hotelId);
        return ResponseEntity.ok(services);
    }

    // Get a service by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServicesDisponiblesDto> getServicesDisponiblesById(@PathVariable Long id) {
        ServicesDisponiblesDto servicesDisponibles = servicesDisponiblesService.getServicesDisponiblesById(id);
        return servicesDisponibles != null ? ResponseEntity.ok(servicesDisponibles) : ResponseEntity.notFound().build();
    }

    // Update a service
    @PutMapping("/{id}")
    public ResponseEntity<ServicesDisponiblesDto> updateServicesDisponibles(@PathVariable Long id, @RequestBody ServicesDisponiblesDto servicesDisponiblesDto) {
        ServicesDisponiblesDto updatedServicesDisponibles = servicesDisponiblesService.updateServicesDisponibles(id, servicesDisponiblesDto);
        return updatedServicesDisponibles != null ? ResponseEntity.ok(updatedServicesDisponibles) : ResponseEntity.notFound().build();
    }

    // Delete a service
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicesDisponibles(@PathVariable Long id) {
        servicesDisponiblesService.deleteServicesDisponibles(id);
        return ResponseEntity.noContent().build();
    }
}
