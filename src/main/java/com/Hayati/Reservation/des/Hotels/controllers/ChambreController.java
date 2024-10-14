package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.ChambreDto;
import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.entity.Chambre;
import com.Hayati.Reservation.des.Hotels.services.ChambreService;

import io.jsonwebtoken.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/chambres")
public class ChambreController {

    private final ChambreService chambreService;

    @Autowired
    public ChambreController(ChambreService chambreService) {
        this.chambreService = chambreService;
    }

    @GetMapping("/list")
public ResponseEntity<List<ChambreDto>> listChambres(@RequestParam(value = "search", required = false) String search) {
    List<ChambreDto> chambres = chambreService.getAllChambres();

    // Filter rooms based on the search query
    if (search != null && !search.isEmpty()) {
        chambres = chambres.stream()
                .filter(chambre -> chambre.getName().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
    }

    chambres.forEach(chambre -> {
        if (chambre.getImageUrl() != null && !chambre.getImageUrl().startsWith("http")) {
            chambre.setImageUrl("http://localhost:9001/" + chambre.getImageUrl());
        }
    });

    return ResponseEntity.ok(chambres);
}

    

    // Obtenir une chambre par ID
    @GetMapping("/{id}")
    public ResponseEntity<ChambreDto> getChambreById(@PathVariable Long id) {
        Optional<ChambreDto> chambre = chambreService.getChambreById(id);
        return chambre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
   // Fetch rooms by hotel ID through suite association
// @GetMapping("/hotel/{hotelId}")
// public ResponseEntity<List<ChambreDto>> getRoomsByHotelId(@PathVariable Long hotelId) {
//     List<ChambreDto> chambres = chambreService.getChambresByHotelId(hotelId);  // Ensure this method is implemented
//     return ResponseEntity.ok(chambres);
// }


@GetMapping("/hotel/{hotelId}")
public ResponseEntity<List<ChambreDto>> getRoomsByHotelId(@PathVariable Long hotelId) {
    List<ChambreDto> chambres = chambreService.getChambresByHotelId(hotelId);

    chambres.forEach(chambre -> {
        if (chambre.getImageUrl() != null && !chambre.getImageUrl().startsWith("http")) {
            chambre.setImageUrl("http://172.20.10.2:9001/" + chambre.getImageUrl());  // Assurez-vous que cette URL est correcte
        }
    });

    return ResponseEntity.ok(chambres);
}






@PostMapping("/create")
public ResponseEntity<?> createChambre(
        @RequestParam("name") String name,
        @RequestParam("prixJour") String prixJourStr,
        @RequestParam("disponibilites") boolean disponibilites,
        @RequestParam("capacite") int capacite,
        @RequestParam("description") String description,
        @RequestParam("suiteId") Long suiteId,
        @RequestParam("photo") MultipartFile photo) throws IOException {

    // Valider la taille du fichier
    if (photo.getSize() > 52428800) { // 50MB en octets
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Fichier trop volumineux !");
    }

    // Parse the string to float
    float prixJour;
    try {
        prixJour = Float.parseFloat(prixJourStr);
    } catch (NumberFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Format du prix invalide !");
    }

    ChambreDto chambreDto = new ChambreDto()
            .setName(name)
            .setPrixJour(prixJour)
            .setDisponibilites(disponibilites)
            .setCapacite(capacite)
            .setDescription(description)
            .setSuite_id(suiteId);

    ChambreDto createdChambre = chambreService.createChambre(chambreDto, photo);

    if (createdChambre != null && createdChambre.getImageUrl() != null) {
        String imageUrl = "http://192.168.100.6:9001/" + createdChambre.getImageUrl();
        createdChambre.setImageUrl(imageUrl);
    }

    return ResponseEntity.ok(createdChambre);
}


    
    // Mise à jour d'une chambre
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateChambre(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("prixJour") String prixJourStr,  // Passed as String, will convert to float
            @RequestParam("disponibilites") boolean disponibilites,
            @RequestParam("capacite") int capacite,  
            @RequestParam("description") String description,
            @RequestParam("suiteId") Long suiteId,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
    
        // Validate and parse prixJour from String to float
        float prixJour;
        try {
            prixJour = Float.parseFloat(prixJourStr);  // Handle exception if invalid number
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid prixJour format");
        }
    
        if (photo != null && photo.getSize() > 52428800) { // 50MB in bytes
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Fichier trop volumineux !");
        }
    
        // Create ChambreDto and set attributes
        ChambreDto chambreDto = new ChambreDto()
                .setName(name)
                .setPrixJour(prixJour)  // Set prixJour as float
                .setDisponibilites(disponibilites)
                .setCapacite(capacite)  // Set the capacity directly
                .setDescription(description)
                .setSuite_id(suiteId);
    
    
        // Update the chambre in the service layer
        ChambreDto updatedChambre = chambreService.updateChambre(id, chambreDto, photo);
    
        // Update image URL if it exists
        if (updatedChambre != null && updatedChambre.getImageUrl() != null) {
            String imageUrl = "http://172.20.10.2:9001/" + updatedChambre.getImageUrl();
            updatedChambre.setImageUrl(imageUrl);
        }
    
        return ResponseEntity.ok(updatedChambre);
    }
    

    // Supprimer une chambre
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteChambre(@PathVariable Long id) {
        chambreService.deleteChambre(id);
        return ResponseEntity.ok("Chambre deleted successfully.");
    }

    private float parsePrixJour(String prixJourStr) {
        // Remove non-numeric characters, assuming input is like "140000MRU/J"
        String numericPart = prixJourStr.replaceAll("[^0-9.]", "");
        return Float.parseFloat(numericPart);
    }
    
}
