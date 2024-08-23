package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.services.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/auth/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<?> createHotel(
            @RequestPart("hotel") String hotelDtoJson,
            @RequestPart("photo") MultipartFile photo) {
        try {
            // Valider que le fichier est bien une image
            if (!photo.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest().body("Le fichier uploadé doit être une image.");
            }

            // Convertir le JSON reçu en objet HotelDto
            ObjectMapper objectMapper = new ObjectMapper();
            HotelDto hotelDto = objectMapper.readValue(hotelDtoJson, HotelDto.class);

            // Gérer l'upload de fichier (photo)
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            String uploadDir = "C:\\Pfe\\Reservation_hotels";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs(); // Crée le dossier s'il n'existe pas
            }
            Files.copy(photo.getInputStream(), Paths.get(uploadDir, fileName), StandardCopyOption.REPLACE_EXISTING);

            // Construire l'URL de l'image
            String imageUrl = "/images/" + fileName;

            // Sauvegarder l'hôtel avec l'URL de l'image
            HotelDto createdHotel = hotelService.createHotel(hotelDto, imageUrl);

            // Retourner l'hôtel créé
            return ResponseEntity.ok(createdHotel);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du téléchargement de l'image.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création de l'hôtel.");
        }
    }

    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        List<HotelDto> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        HotelDto hotel = hotelService.getHotelById(id);
        return hotel != null ? ResponseEntity.ok(hotel) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto) {
        HotelDto updatedHotel = hotelService.updateHotel(id, hotelDto);
        return updatedHotel != null ? ResponseEntity.ok(updatedHotel) : ResponseEntity.notFound().build();
    }
    @GetMapping("/api/auth/hotel")
public ResponseEntity<List<HotelDto>> getAllHotels(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String token = authHeader.substring(7);
    // Vérifiez le token ici
    List<HotelDto> hotels = hotelService.getAllHotels();
    return ResponseEntity.ok(hotels);
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}
