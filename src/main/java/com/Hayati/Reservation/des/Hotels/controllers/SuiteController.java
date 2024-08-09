package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.SuiteDto;
import com.Hayati.Reservation.des.Hotels.services.SuiteService;
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
@RequestMapping("/api/auth/Suite")
public class SuiteController {

    @Autowired
    private SuiteService suiteService;

    @PostMapping
    public ResponseEntity<SuiteDto> createSuite(
            @RequestPart("suite") String suiteDtoJson,
            @RequestPart("photo") MultipartFile photo) {
        try {
            // Deserialize the suite DTO
            ObjectMapper objectMapper = new ObjectMapper();
            SuiteDto suiteDto = objectMapper.readValue(suiteDtoJson, SuiteDto.class);

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

            // Save the suite information along with the image URL
            SuiteDto createdSuite = suiteService.createSuite(suiteDto, imageUrl);

            return ResponseEntity.ok(createdSuite);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<SuiteDto>> getAllSuites() {
        List<SuiteDto> suites = suiteService.getAllSuites();
        return ResponseEntity.ok(suites);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuiteDto> getSuiteById(@PathVariable Long id) {
        SuiteDto suite = suiteService.getSuiteById(id);
        return suite != null ? ResponseEntity.ok(suite) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuiteDto> updateSuite(
            @PathVariable Long id,
            @RequestPart("suite") String suiteDtoJson,
            @RequestPart("photo") MultipartFile photo) {
        try {
            // Deserialize the suite DTO
            ObjectMapper objectMapper = new ObjectMapper();
            SuiteDto suiteDto = objectMapper.readValue(suiteDtoJson, SuiteDto.class);

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

            // Update the suite information along with the image URL
            SuiteDto updatedSuite = suiteService.updateSuite(id, suiteDto, imageUrl);

            return ResponseEntity.ok(updatedSuite);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuite(@PathVariable Long id) {
        suiteService.deleteSuite(id);
        return ResponseEntity.noContent().build();
    }
}
