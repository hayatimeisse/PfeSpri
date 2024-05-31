package com.Hayati.Reservation.des.Hotels.controllers;


import com.Hayati.Reservation.des.Hotels.dto.ChambreDto;
import com.Hayati.Reservation.des.Hotels.dto.PaiementDto;
import com.Hayati.Reservation.des.Hotels.services.ChambreService;
import com.Hayati.Reservation.des.Hotels.services.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/Chambre")
public class ChambreController {

    @Autowired
    private ChambreService chambreService;

    @PostMapping
    public ResponseEntity<ChambreDto> createChambre(@RequestBody ChambreDto chambreDto) {
        ChambreDto createdChambre = chambreService.createChambre(chambreDto);
        return ResponseEntity.ok(createdChambre);
    }

    @GetMapping
    public ResponseEntity<List<ChambreDto>> getAllChambres() {
        List<ChambreDto> chambres = chambreService.getAllChambres();
        return ResponseEntity.ok(chambres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChambreDto> getChambreById(@PathVariable Long id) {
        ChambreDto chambre = chambreService.getChambreById(id);
        return chambre != null ? ResponseEntity.ok(chambre) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChambreDto> updateChambre(@PathVariable Long id, @RequestBody ChambreDto chambreDto) {
        ChambreDto updatedChambre = chambreService.updateChambre(id, chambreDto);
        return updatedChambre != null ? ResponseEntity.ok(updatedChambre) : ResponseEntity.notFound().build();
    }
  

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChambre(@PathVariable Long id) {
        chambreService.deleteChambre(id);
        return ResponseEntity.noContent().build();
    }
}
