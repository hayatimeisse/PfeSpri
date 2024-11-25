package com.Hayati.Reservation.des.Hotels.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.Hayati.Reservation.des.Hotels.dto.RegisterSubscribeDto;
import com.Hayati.Reservation.des.Hotels.dto.UpdateSubscribeDto;
import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.enumeration.Status;
import com.Hayati.Reservation.des.Hotels.repositoriy.SubscribeRepository;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubscribeService {
      @Autowired
    private SubscribeRepository subscribeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Directory path to save client's files (photos)
    private final String BASE_IMAGE_UPLOAD_DIR = "C:/Pfe/Reservation_Subscribe/";

    // Base URL for accessing images
    private final String BASE_IMAGE_URL = "http://192.168.100.108:9001/";

    public Subscribe findByVerificationCode(String code) {
        return subscribeRepository.findByVerificationCode(code);
    }
    public Optional<Subscribe> findById(Long id) {
        return subscribeRepository.findById(id);
    }

    public boolean verifyEmail(Long userId, String code) {
        Subscribe subscribe = subscribeRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        if (subscribe.getVerificationCode().equals(code)) {
            subscribe.setStatus(Status.ATTEND); // Activer l'utilisateur
            subscribe.setVerificationCode(null); // Supprimer le code
            subscribeRepository.save(subscribe);
            return true;
        }
        return false;
    }


   
    private String saveImageSubscribe(MultipartFile photo, String subDir) {
        try {
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            Path path = Paths.get(BASE_IMAGE_UPLOAD_DIR + subDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, photo.getBytes());
            return subDir + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Échec de l'enregistrement de l'image", e);
        }
    }
    

    public Subscribe createSubscribe(RegisterSubscribeDto input) {
        Subscribe subscribe = new Subscribe();
        subscribe.setName(input.getNom());
        subscribe.setEmail(input.getEmail());
        subscribe.setPassword(passwordEncoder.encode(input.getPassword()));
        subscribe.getRoles().add("ROLE_SUBSCRIBE");
        subscribe.setStatus(Status.ATTEND);

        // Save photo if provided
        // if (input.getPhoto() != null && !input.getPhoto().isEmpty()) {
        //     String photoPath = saveImageSubscribe(input.getPhoto(), "subscribe_photos/");
        //     subscribe.setPhoto(photoPath);
        // }
  
    

        // Use subscribeRepository.save() to save the subscribe entity
        return subscribeRepository.save(subscribe);
    }
    public Subscribe save(Subscribe subscribe) {
        return subscribeRepository.save(subscribe);
    }
    public Subscribe updateSubscribe(UpdateSubscribeDto updateSubscribeDto, Long id) {
        // Check if the subscribe exists
        Subscribe subscribe = subscribeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscribe not found with id: " + id));
    
        // Update the subscribe fields
        subscribe.setEmail(updateSubscribeDto.getEmail());
        subscribe.setPassword(passwordEncoder.encode(updateSubscribeDto.getPassword())); // Encrypt password if needed
        subscribe.setName(updateSubscribeDto.getNom());
        subscribe.setStatus(updateSubscribeDto.getStatus());
    
        // Handle the photo if provided
        if (updateSubscribeDto.getPhoto() != null && !updateSubscribeDto.getPhoto().isEmpty()) {
            String photoPath = saveImageSubscribe(updateSubscribeDto.getPhoto(), "subscribe_photos/");
            // subscribe.setPhoto(photoPath);  // Or save the photo URL as required
        }
    
        return subscribeRepository.save(subscribe);
    }
    

    // Helper method to save the photo and return its path
    private String savePhoto(MultipartFile photo) {
        try {
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            Path filePath = Paths.get("subscribe_photos/" + fileName);
            Files.write(filePath, photo.getBytes());
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save photo", e);
        }
    }
    public Subscribe updateSubscribeProfile(Long userId, UpdateSubscribeDto updateDto, MultipartFile photo) {
        Subscribe existingSubscribe = subscribeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update email and password
        existingSubscribe.setEmail(updateDto.getEmail());
        if (updateDto.getPassword() != null && !updateDto.getPassword().isEmpty()) {
            existingSubscribe.setPassword(passwordEncoder.encode(updateDto.getPassword()));
        }

        // Save photo if provided
        // if (photo != null && !photo.isEmpty()) {
        //     String photoPath = savePhoto(photo);
        //     existingSubscribe.setPhoto(photoPath);
        // }

        // Save updated user profile
        return subscribeRepository.save(existingSubscribe);
    }
    // Get all clients
    @PreAuthorize("hasRole('ADMIN')")
    public List<Subscribe> getAllSubscribes() {
        return subscribeRepository.findAll();
    }

    // Get client by ID
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Subscribe> getSubscribeById(Long id) {
        return subscribeRepository.findById(id);
    }

    // Delete a client
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSubscribe(Long id) {
        subscribeRepository.findById(id).ifPresentOrElse(subscribeRepository::delete, 
            () -> {
                throw new RuntimeException("Subscribe not found with id: " + id);
            });
    }

    // Get the count of clients
    @PreAuthorize("hasRole('ADMIN')")
    public long getSubscribeCount() {
        return subscribeRepository.count();
    }
}
