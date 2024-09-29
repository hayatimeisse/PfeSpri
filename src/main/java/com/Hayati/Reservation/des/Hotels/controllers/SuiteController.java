package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.SuiteDto;
import com.Hayati.Reservation.des.Hotels.services.SuiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/suites")  // Added '/suites' to avoid conflict with other controllers
public class SuiteController {

    private final SuiteService suiteService;

    @Autowired
    public SuiteController(SuiteService suiteService) {
        this.suiteService = suiteService;
    }

    // Lister toutes les suites
    @GetMapping("/list")
    public ResponseEntity<List<SuiteDto>> listSuites() {
        List<SuiteDto> suites = suiteService.getAllSuites();
        return ResponseEntity.ok(suites);
    }

    // Obtenir une suite par ID
    @GetMapping("/{id}")
    public ResponseEntity<SuiteDto> getSuiteById(@PathVariable Long id) {
        Optional<SuiteDto> suite = suiteService.getSuiteById(id);
        return suite.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Créer une nouvelle suite
    @PostMapping("/create")
    public ResponseEntity<?> createSuite(
            @RequestParam("prixJour") String prixJourStr,
            @RequestParam("disponibilites") boolean disponibilites,
            @RequestParam("description") String description,
            @RequestParam("hotelId") Long hotelId,
            @RequestParam("photo") MultipartFile photo) {

        try {
            float prixJour = parsePrixJour(prixJourStr);  // Parse the price
            SuiteDto suiteDto = new SuiteDto()
                    .setPrixJour(prixJour)
                    .setDisponibilites(disponibilites)
                    .setDescription(description)
                    .setHotel_id(hotelId);

            SuiteDto createdSuite = suiteService.createSuite(suiteDto, photo);
            return ResponseEntity.ok(createdSuite);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid price format: " + prixJourStr);
        }
    }

    // Mise à jour d'une suite
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSuite(
            @PathVariable Long id,
            @RequestParam("prixJour") String prixJourStr,
            @RequestParam("disponibilites") boolean disponibilites,
            @RequestParam("description") String description,
            @RequestParam("hotelId") Long hotelId,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {

        try {
            float prixJour = parsePrixJour(prixJourStr);  // Parse the price

            SuiteDto suiteDto = new SuiteDto()
                    .setPrixJour(prixJour)
                    .setDisponibilites(disponibilites)
                    .setDescription(description)
                    .setHotel_id(hotelId);

            SuiteDto updatedSuite = suiteService.updateSuite(id, suiteDto, photo);
            return ResponseEntity.ok(updatedSuite);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid price format: " + prixJourStr);
        }
    }

    // Supprimer une suite
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSuite(@PathVariable Long id) {
        suiteService.deleteSuite(id);
        return ResponseEntity.ok("Suite deleted successfully.");
    }

    private float parsePrixJour(String prixJourStr) {
        // Remove non-numeric characters, assuming input is like "140000MRU/J"
        String numericPart = prixJourStr.replaceAll("[^0-9.]", "");
        return Float.parseFloat(numericPart);
    }
}
