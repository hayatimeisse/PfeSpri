package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.ChambreDto;
import com.Hayati.Reservation.des.Hotels.services.ChambreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/auth/chambre")
public class ChambreController {

    @Autowired
    private ChambreService chambreService;

    private static final String UPLOAD_DIR = "C:\\Pfe\\Reservation_hotels\\";

    @PostMapping
    public ResponseEntity<ChambreDto> createChambre(
            @RequestPart("chambre") String chambreDtoJson,
            @RequestPart("photo") MultipartFile photo) {
        try {
            // Désérialisation de l'objet JSON en DTO Chambre
            ObjectMapper objectMapper = new ObjectMapper();
            ChambreDto chambreDto = objectMapper.readValue(chambreDtoJson, ChambreDto.class);

            // Gestion de l'upload du fichier image
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            File uploadDirFile = new File(UPLOAD_DIR);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            Files.copy(photo.getInputStream(), Paths.get(UPLOAD_DIR + fileName), StandardCopyOption.REPLACE_EXISTING);

            // Construction de l'URL de l'image
            String imageUrl = "/images/" + fileName;

            // Enregistrement des informations de la chambre avec l'URL de l'image
            ChambreDto createdChambre = chambreService.createChambre(chambreDto, imageUrl);

            return ResponseEntity.ok(createdChambre);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);  // Gérer l'exception de manière appropriée
        }
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
    public ResponseEntity<ChambreDto> updateChambre(
            @PathVariable Long id,
            @RequestPart("chambre") String chambreDtoJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        try {
            // Désérialisation de l'objet JSON en DTO Chambre
            ObjectMapper objectMapper = new ObjectMapper();
            ChambreDto chambreDto = objectMapper.readValue(chambreDtoJson, ChambreDto.class);

            String imageUrl = null;

            // Gestion de l'upload du fichier si une nouvelle image est fournie
            if (photo != null && !photo.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                File uploadDirFile = new File(UPLOAD_DIR);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }
                Files.copy(photo.getInputStream(), Paths.get(UPLOAD_DIR + fileName), StandardCopyOption.REPLACE_EXISTING);
                imageUrl = "/images/" + fileName;  // URL de la nouvelle image
            } else {
                // Conserver l'image existante si aucune nouvelle image n'est fournie
                imageUrl = chambreService.getChambreById(id).getImageUrl();
            }

            // Mise à jour de la chambre avec l'URL de l'image
            ChambreDto updatedChambre = chambreService.updateChambre(id, chambreDto, imageUrl);

            return ResponseEntity.ok(updatedChambre);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);  // Gérer l'exception de manière appropriée
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChambre(@PathVariable Long id) {
        chambreService.deleteChambre(id);
        return ResponseEntity.noContent().build();  // Retourner une réponse avec un statut 204 (No Content)
    }
}
