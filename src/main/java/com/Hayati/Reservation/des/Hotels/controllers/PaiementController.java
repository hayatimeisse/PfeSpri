package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.PaiementDto;
import com.Hayati.Reservation.des.Hotels.services.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/Paiement")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    @PostMapping
    public ResponseEntity<PaiementDto> createPaiement(@RequestBody PaiementDto paiementDto) {
        PaiementDto createdPaiement = paiementService.createPaiement(paiementDto);
        return ResponseEntity.ok(createdPaiement);
    }

    @GetMapping
    public ResponseEntity<List<PaiementDto>> getAllPaiements() {
        List<PaiementDto> paiements = paiementService.getAllPaiements();
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaiementDto> getPaiementById(@PathVariable Long id) {
        PaiementDto paiement = paiementService.getPaiementById(id);
        return paiement != null ? ResponseEntity.ok(paiement) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaiementDto> updatePaiement(@PathVariable Long id, @RequestBody PaiementDto paiementDto) {
        PaiementDto updatedPaiement = paiementService.updatePaiement(id, paiementDto);
        return updatedPaiement != null ? ResponseEntity.ok(updatedPaiement) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }
}
