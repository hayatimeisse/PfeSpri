package com.Hayati.Reservation.des.Hotels.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

import com.Hayati.Reservation.des.Hotels.dto.RegisterClientDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterSubscribeDto;
import com.Hayati.Reservation.des.Hotels.dto.UpdateClientDto;
import com.Hayati.Reservation.des.Hotels.dto.UpdateSubscribeDto;
import com.Hayati.Reservation.des.Hotels.entity.Client;
import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.enumeration.Status;
import com.Hayati.Reservation.des.Hotels.services.SubscribeService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth/subscribe")
@RestController
public class SubscribeController {
    private final SubscribeService subscribeService;

    @Autowired
    public SubscribeController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    // List all clients
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Subscribe>> listSubscribes() {
        List<Subscribe> subscribes = subscribeService.getAllSubscribes();

        // Append full image URL to each client
        subscribes.forEach(subscribe -> {
            if (subscribe.getPhoto() != null && !subscribe.getPhoto().isEmpty()) {
                String imageUrl = "http://localhost:9001/" + subscribe.getPhoto();
                subscribe.setPhoto(imageUrl);
            }
        });
        return ResponseEntity.ok(subscribes);
    }

    // Get a specific client by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Subscribe> getSubscribeById(@PathVariable Long id) {
        Optional<Subscribe> subscribe = subscribeService.getSubscribeById(id);
        if (subscribe.isPresent()) {
            Subscribe s = subscribe.get();
            if (s.getPhoto() != null && !s.getPhoto().isEmpty()) {
                String imageUrl = "http://localhost:9001" + s.getPhoto();
                s.setPhoto(imageUrl);
            }
            return ResponseEntity.ok(s);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/{id}/{status}")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> updateSubscribeStatus(@PathVariable Long id, @PathVariable String status) {
    try {
        Optional<Subscribe> optionalSubscribe = subscribeService.getSubscribeById(id);

        if (optionalSubscribe.isPresent()) {
            Subscribe subscribe = optionalSubscribe.get();

            // Validate and update the status
            if ("accepted".equalsIgnoreCase(status) || "rejected".equalsIgnoreCase(status) || "attend".equalsIgnoreCase(status)) {
                subscribe.setStatus(Status.valueOf(status.toUpperCase())); // Update status
                subscribeService.save(subscribe); // Save the updated entity
                return ResponseEntity.ok("Subscription status updated to " + status);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status value");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found");
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status: " + e.getMessage());
    }
}



    // Delete a client
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSubscribe(@PathVariable Long id) {
        subscribeService.deleteSubscribe(id);
        return ResponseEntity.ok("Subscribe with ID " + id + " has been deleted successfully.");
    }

    // Create a new client
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createSubscribe(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("nom") String nom,
            @RequestParam("photo") MultipartFile photo,
                 @RequestParam("status") Status status) {

        RegisterSubscribeDto registerSubscribeDto = new RegisterSubscribeDto()
                .setEmail(email)
                .setPassword(password)
                .setNom(nom)
                .setStatus(status)
                .setPhoto(photo);

                Subscribe createdSubscribe = subscribeService.createSubscribe(registerSubscribeDto);
        if (createdSubscribe != null && createdSubscribe.getPhoto() != null) {
            String imageUrl = "http://localhost:9001/" + createdSubscribe.getPhoto();
            createdSubscribe.setPhoto(imageUrl);
        }
        return ResponseEntity.ok(createdSubscribe);
    }

    // Get the count of clients
    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getCountOfSubscribes() {
        long count = subscribeService.getSubscribeCount();
        return ResponseEntity.ok(count);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSubscribe(
            @PathVariable Long id,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("nom") String nom,
            @RequestParam("status") Status status,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
    
        // Create a DTO with the provided request parameters
        UpdateSubscribeDto updateSubscribeDto = new UpdateSubscribeDto()
                .setEmail(email)
                .setPassword(password)
                .setNom(nom)
                .setStatus(status)
                .setPhoto(photo);
    
        try {
            // Call the service to update the subscribe object
            Subscribe updatedSubscribe = subscribeService.updateSubscribe(updateSubscribeDto, id);
    
            // Check if the photo is valid and prepend the base URL if necessary
            if (updatedSubscribe.getPhoto() != null 
                    && !updatedSubscribe.getPhoto().startsWith("http") 
                    && !updatedSubscribe.getPhoto().isEmpty()) {
                String imageUrl = "http://localhost:9001/" + updatedSubscribe.getPhoto();
                updatedSubscribe.setPhoto(imageUrl);
            }
    
            // Return the updated subscribe object
            return ResponseEntity.ok(updatedSubscribe);
    
        } catch (RuntimeException e) {
            // Return an error response if the subscribe could not be updated
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscribe not found or update failed.");
        }
    }    
}
