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
import com.Hayati.Reservation.des.Hotels.repositoriy.SubscribeRepository;
import java.util.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class SubscribeService {
      @Autowired
    private SubscribeRepository subscribeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Directory path to save client's files (photos)
    private final String IMAGE_UPLOAD_DIR = "C:/Pfe/Reservation_Subscribe/";

    // Base URL for accessing images
    private final String BASE_IMAGE_URL = "http://localhost:9001/";

    // Méthode pour sauvegarder les fichiers clients
    private String saveImage(MultipartFile photo, String subDir) {
        try {
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            Path path = Paths.get(IMAGE_UPLOAD_DIR + subDir + fileName);
            Files.createDirectories(path.getParent()); // Crée le répertoire si nécessaire
            Files.write(path, photo.getBytes());
            return subDir + fileName;  // Retourner le chemin relatif
        } catch (IOException e) {
            throw new RuntimeException("Échec de l'enregistrement de l'image", e);
        }
    }

    // Méthode de création d'un client avec téléchargement de photo
    public Subscribe createSubscribe(RegisterSubscribeDto input) {
        // Créer un nouvel objet client
        Subscribe subscribe = new Subscribe();
        subscribe.setName(input.getNom());
        subscribe.setEmail(input.getEmail());
        subscribe.setPassword(passwordEncoder.encode(input.getPassword()));
    
        // Ajouter le rôle 'ROLE_CLIENT'
        subscribe.getRoles().add("ROLE_SUBSCRIBE");
    
        // Enregistrer le client dans la base de données
        Subscribe savedSubscribe = subscribeRepository.save(subscribe);
    
        // Générer l'URL complète pour l'image si elle est présente
        if (savedSubscribe.getPhoto() != null) {
            savedSubscribe.setPhoto("http://localhost:9001/" + savedSubscribe.getPhoto());
        }
    
        // Retourner le client avec les rôles associés
        return savedSubscribe;
    }
    
    

    // Update an existing client
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Subscribe updateSubscribe(UpdateSubscribeDto input, Long id) {
        return subscribeRepository.findById(id).map(existingSubscribe -> {
            // If there's a new photo, update it
            if (input.getPhoto() != null && !input.getPhoto().isEmpty()) {
                String photoPath = saveImage(input.getPhoto(), "subscribe_photos/");
                existingSubscribe.setPhoto(photoPath);
            }

            existingSubscribe.setName(input.getNom());
            existingSubscribe.setEmail(input.getEmail());
            if (input.getPassword() != null && !input.getPassword().isEmpty()) {
                existingSubscribe.setPassword(passwordEncoder.encode(input.getPassword()));
            }

            Subscribe updatedSubscribe = subscribeRepository.save(existingSubscribe);

            // Set full URL for the photo
            updatedSubscribe.setPhoto("http://localhost:9001/" + updatedSubscribe.getPhoto());

            return updatedSubscribe;
        }).orElseThrow(() -> new RuntimeException("Subscribe not found"));
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
