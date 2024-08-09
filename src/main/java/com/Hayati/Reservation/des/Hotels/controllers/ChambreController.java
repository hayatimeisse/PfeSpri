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

    @PostMapping
    public ResponseEntity<ChambreDto> createChambre(
            @RequestPart("chambre") String chambreDtoJson,
            @RequestPart("photo") MultipartFile photo) {
        try {
            // Deserialize the chambre DTO
            ObjectMapper objectMapper = new ObjectMapper();
            ChambreDto chambreDto = objectMapper.readValue(chambreDtoJson, ChambreDto.class);

            // Handle the file upload
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            String uploadDir = "src/main/resources/static/images/";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            Files.copy(photo.getInputStream(), Paths.get(uploadDir + fileName), StandardCopyOption.REPLACE_EXISTING);

            // Construct the image URL
            String imageUrl = "/images/" + fileName;

            // Save the chambre information along with the image URL
            ChambreDto createdChambre = chambreService.createChambre(chambreDto, imageUrl);

            return ResponseEntity.ok(createdChambre);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
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
            @RequestPart("photo") MultipartFile photo) {
        try {
            // Deserialize the chambre DTO
            ObjectMapper objectMapper = new ObjectMapper();
            ChambreDto chambreDto = objectMapper.readValue(chambreDtoJson, ChambreDto.class);

            // Handle the file upload
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            String uploadDir = "src/main/resources/static/images/";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            Files.copy(photo.getInputStream(), Paths.get(uploadDir + fileName), StandardCopyOption.REPLACE_EXISTING);

            // Construct the image URL
            String imageUrl = "/images/" + fileName;

            // Update the chambre information along with the image URL
            ChambreDto updatedChambre = chambreService.updateChambre(id, chambreDto, imageUrl);

            return ResponseEntity.ok(updatedChambre);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChambre(@PathVariable Long id) {
        chambreService.deleteChambre(id);
        return ResponseEntity.noContent().build();
    }
}
