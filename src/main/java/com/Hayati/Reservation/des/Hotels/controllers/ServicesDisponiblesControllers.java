package com.Hayati.Reservation.des.Hotels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.Hayati.Reservation.des.Hotels.dto.ServicesDisponiblesDto;
import com.Hayati.Reservation.des.Hotels.services.ServicesDisponiblesService;

@RestController
@RequestMapping("/api/auth/Services")
public class ServicesDisponiblesControllers {

    @Autowired
    private ServicesDisponiblesService servicesDisponiblesService;

    @PostMapping
    public ResponseEntity<ServicesDisponiblesDto> createServicesDisponibles(@RequestBody ServicesDisponiblesDto servicesDisponiblesDto) {
        ServicesDisponiblesDto createdServicesDisponibles = servicesDisponiblesService.createServicesDisponibles(servicesDisponiblesDto);
        return ResponseEntity.ok(createdServicesDisponibles);
    }

    @GetMapping
    public ResponseEntity<List<ServicesDisponiblesDto>> getAllServicesDisponibless() {
        List<ServicesDisponiblesDto> servicesDisponibless = servicesDisponiblesService.getAllServicesDisponibless();
        return ResponseEntity.ok(servicesDisponibless);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicesDisponiblesDto> getServicesDisponiblesById(@PathVariable Long id) {
        ServicesDisponiblesDto servicesDisponibles = servicesDisponiblesService.getServicesDisponiblesById(id);
        return servicesDisponibles != null ? ResponseEntity.ok(servicesDisponibles) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicesDisponiblesDto> updateServicesDisponibles(@PathVariable Long id, @RequestBody ServicesDisponiblesDto servicesDisponiblesDto) {
        ServicesDisponiblesDto updatedServicesDisponibles = servicesDisponiblesService.updateServicesDisponibles(id, servicesDisponiblesDto);
        return updatedServicesDisponibles != null ? ResponseEntity.ok(updatedServicesDisponibles) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicesDisponibles(@PathVariable Long id) {
        servicesDisponiblesService.deleteServicesDisponibles(id);
        return ResponseEntity.noContent().build();
    }
}
