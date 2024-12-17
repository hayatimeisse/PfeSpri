package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.ChambreDto;
import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.dto.SuiteDto;
import com.Hayati.Reservation.des.Hotels.entity.Suite;
import com.Hayati.Reservation.des.Hotels.repositoriy.SuiteRepositoriy;
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
        this.suiteService = suiteService; // Initialize suiteService
    }
  

    @GetMapping("/hotel/{hotelId}/subscribe/{subscribeId}")
    public List<Suite> getSuitesByHotelAndSubscribe(@PathVariable Long hotelId, @PathVariable Long subscribeId) {
        return suiteService.getSuitesByHotelAndSubscribe(hotelId, subscribeId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SuiteDto>> listSuites(@RequestParam(value = "search", required = false) String search) {
        List<SuiteDto> suites = suiteService.getAllSuites();
    
        // Ensure that the image URLs are correctly prefixed with the base URL
        suites.forEach(suite -> {
            if (suite.getImageUrl() != null && !suite.getImageUrl().startsWith("http")) {
                suite.setImageUrl("http://192.168.100.4:9001" + suite.getImageUrl());
            }
        });
    
        return ResponseEntity.ok(suites);
    }
    
    // Obtenir une suite par ID
    @GetMapping("/{id}")
    public ResponseEntity<SuiteDto> getSuiteById(@PathVariable Long id) {
        Optional<SuiteDto> suite = suiteService.getSuiteById(id);
        return suite.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PostMapping("/create")
    public ResponseEntity<?> createSuite(
        @RequestParam("prixJour") String prixJourStr,
        @RequestParam("disponibilites") boolean disponibilites,
        @RequestParam("description") String description,
        @RequestParam("hotelId") Long hotelId,
        @RequestParam("photo") MultipartFile photo) {
    
        // Valider la taille du fichier
        if (photo.getSize() > 52428800) { // 50MB en octets
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Fichier trop volumineux !");
        }
    
        float prixJour;
        try {
            prixJour = Float.parseFloat(prixJourStr);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Format du prix invalide !");
        }
    
        SuiteDto suiteDto = new SuiteDto()
                    .setDisponibilites(disponibilites)
                    .setDescription(description)
                    .setHotel_id(hotelId)
                    .setPrixJour(prixJour); // Ajoutez cette ligne
    
        SuiteDto createdSuite = suiteService.createSuite(suiteDto, photo);
    
        if (createdSuite != null && createdSuite.getImageUrl() != null) {
            String imageUrl = "http://192.168.100.4:9001/" + createdSuite.getImageUrl();
            createdSuite.setImageUrl(imageUrl);
        }
    
        return ResponseEntity.ok(createdSuite);
    }
    
    // Mettre à jour un hôtel
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSuite(
        @PathVariable Long id,
        @RequestParam("prixJour") String prixJourStr,  // Passed as String, will convert to float
        @RequestParam("disponibilites") boolean disponibilites,
        @RequestParam("description") String description,
        @RequestParam("hotelId") Long hotelId,
        @RequestParam(value = "photo", required = false) MultipartFile photo) {
    
        // Valider et convertir prixJour de String à float
        float prixJour;
        try {
            prixJour = Float.parseFloat(prixJourStr);  // Gestion de l'exception en cas de format invalide
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Format du prixJour invalide");
        }
    
        // Vérifier si la taille du fichier photo est valide
        if (photo != null && photo.getSize() > 52428800) { // 50MB en octets
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Fichier trop volumineux !");
        }
    
        // Créer un DTO pour la suite et définir les attributs
        SuiteDto suiteDto = new SuiteDto()
                .setPrixJour(prixJour)  // Définir prixJour comme float
                .setDisponibilites(disponibilites)
                .setDescription(description)
                .setHotel_id(hotelId);
    
        // Mettre à jour la suite dans la couche service
        SuiteDto updatedSuite = suiteService.updateSuite(id, suiteDto, photo);
    
        // Mettre à jour l'URL de l'image si elle existe
        if (updatedSuite != null && updatedSuite.getImageUrl() != null) {
            String imageUrl = "http://192.168.100.4:9001/" + updatedSuite.getImageUrl();
            updatedSuite.setImageUrl(imageUrl);  // Définir l'URL complète de l'image
        }
    
        return ResponseEntity.ok(updatedSuite);  // Retourner la réponse mise à jour
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
    @GetMapping("/count")
    public ResponseEntity<Long> getCountOfSuites() {
        long count = suiteService.getSuiteCount();
        return ResponseEntity.ok(count);
    }
    // @GetMapping("/hotel/{hotelId}")
    // public ResponseEntity<List<SuiteDto>> getSuitesByHotelId(@PathVariable Long hotelId) {
    //     List<SuiteDto> suites = suiteService.getSuitesForHotel(hotelId);
    //     return ResponseEntity.ok(suites); // Always return 200 with empty or populated list
    // }
    
}
