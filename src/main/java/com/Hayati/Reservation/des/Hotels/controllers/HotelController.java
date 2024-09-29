package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;
import com.Hayati.Reservation.des.Hotels.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    // Lister tous les hôtels
    // Updated method in HotelController to support search query
@GetMapping("/list")
public ResponseEntity<List<HotelDto>> listHotels(@RequestParam(value = "search", required = false) String search) {
    List<HotelDto> hotels = hotelService.getAllHotels();

    // Filter hotels based on the search query
    if (search != null && !search.isEmpty()) {
        hotels = hotels.stream()
                .filter(hotel -> hotel.getName().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
    }

    hotels.forEach(hotel -> {
        if (hotel.getImageUrl() != null && !hotel.getImageUrl().startsWith("http")) {
            hotel.setImageUrl("http://localhost:9001/" + hotel.getImageUrl());
        }
    });

    return ResponseEntity.ok(hotels);
}

    

    // Obtenir un hôtel par ID
    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        Optional<HotelDto> hotel = hotelService.getHotelById(id);

        if (hotel.isPresent()) {
            HotelDto h = hotel.get();
            if (h.getImageUrl() != null && !h.getImageUrl().isEmpty()) {
                String imageUrl = "http://localhost:9000/" + h.getImageUrl();
                h.setImageUrl(imageUrl);
            }
            return ResponseEntity.ok(h);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Supprimer un hôtel
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.ok("Hôtel avec ID " + id + " supprimé avec succès.");
    }

    // Créer un nouvel hôtel
    @PostMapping("/create/hotel")
    public ResponseEntity<?> createHotel(
            @RequestParam("name") String name,
            @RequestParam("emplacement") String emplacement,
            @RequestParam("evaluation") String evaluation,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("commentaires") String commentaires,
            @RequestParam("notifications") String notifications,
            @RequestParam("description") String description ,
            @RequestParam("photo") MultipartFile photo) {

        // Valider la taille du fichier
        if (photo.getSize() > 52428800) { // 50MB en octets
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Fichier trop volumineux !");
        }

        HotelDto hotelDto = new HotelDto()
                .setName(name)
                .setEmplacement(emplacement)
                .setEvaluation(evaluation)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .setCommentaires(commentaires)
                .setNotifications(notifications)
                .setDescription(description);

        HotelDto createdHotel = hotelService.createHotel(hotelDto, photo);

        if (createdHotel != null && createdHotel.getImageUrl() != null) {
            String imageUrl = "http://192.168.100.6:9000/" + createdHotel.getImageUrl();
            createdHotel.setImageUrl(imageUrl);
        }

        return ResponseEntity.ok(createdHotel);
    }

    // Mettre à jour un hôtel
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHotel(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("emplacement") String emplacement,
            @RequestParam("evaluation") String evaluationStr,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("commentaires") String commentaires,
            @RequestParam("notifications") String notifications,
            @RequestParam("description") String description,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
    
        float evaluation;
        try {
            evaluation = Float.parseFloat(evaluationStr);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Format d'évaluation invalide");
        }
    
        HotelDto hotelDto = new HotelDto()
                .setName(name)
                .setEmplacement(emplacement)
                .setEvaluation(String.valueOf(evaluation))  // Conversion en String ici
                .setLatitude(latitude)
                .setLongitude(longitude)
                .setCommentaires(commentaires)
                .setNotifications(notifications)
                .setDescription(description);
    
        HotelDto updatedHotel = hotelService.updateHotel(id, hotelDto, photo);
    
        if (updatedHotel != null && updatedHotel.getImageUrl() != null) {
            String imageUrl = "http://localhost:9000/" + updatedHotel.getImageUrl();
            updatedHotel.setImageUrl(imageUrl);
        }
    
        return ResponseEntity.ok(updatedHotel);
    }
    

    // Compter tous les hôtels
    @GetMapping("/count")
    public ResponseEntity<Long> getCountOfHotels() {
        long count = hotelService.getHotelCount();
        return ResponseEntity.ok(count);
    }
}
