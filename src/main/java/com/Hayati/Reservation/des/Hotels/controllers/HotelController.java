package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.dto.SuiteDto;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;
import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.enumeration.Status;
import com.Hayati.Reservation.des.Hotels.services.HotelService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import io.jsonwebtoken.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping("/hotels/subscribe/{subscribeId}")
    public ResponseEntity<List<HotelDto>> getHotelsBySubscribeId(@PathVariable Long subscribeId) {
        List<HotelDto> hotels = hotelService.getHotelsBySubscribeId(subscribeId);
        return ResponseEntity.ok(hotels);
    }
  

    @PostMapping("/create/hotel")
    public ResponseEntity<HotelDto> createHotel(
            @RequestParam("name") String name,
            @RequestParam("emplacement") String emplacement,
            @RequestParam("evaluation") String evaluation,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("commentaires") String commentaires,
            @RequestParam("notifications") String notifications,
            @RequestParam("description") String description,
            @RequestParam("status") String status,
            @RequestParam("photo") MultipartFile photo,
            @AuthenticationPrincipal Subscribe authenticatedUser) throws IOException {
    
        if (authenticatedUser == null || !(authenticatedUser instanceof Subscribe)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    
        Long subscribe_id = authenticatedUser.getId();
    
        Status enumStatus;
        try {
            enumStatus = Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new HotelDto().setName("Invalid status value"));
        }
    
        HotelDto hotelDto = new HotelDto()
                .setName(name)
                .setEmplacement(emplacement)
                .setEvaluation(evaluation)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .setCommentaires(commentaires)
                .setNotifications(notifications)
                .setStatus(enumStatus)
                .setDescription(description)
                .setSubscribe_id(subscribe_id);
    
        HotelDto createdHotel = hotelService.createHotel(hotelDto, photo);
    
        if (createdHotel != null && createdHotel.getImageUrl() != null) {
            String imageUrl = "http://192.168.100.4:9001/" + createdHotel.getImageUrl();
            createdHotel.setImageUrl(imageUrl);
        }
    
        return ResponseEntity.ok(createdHotel);
    }
    
    
    @PostMapping("/create/hotel/web")
    public ResponseEntity<HotelDto> createHotelForWeb(
            @RequestParam("name") String name,
            @RequestParam("emplacement") String emplacement,
            @RequestParam("evaluation") String evaluation,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("commentaires") String commentaires,
            @RequestParam("notifications") String notifications,
            @RequestParam("description") String description,
            @RequestParam("status") String status,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("subscribe_id") Long subscribe_id) throws IOException {
    
        // Validate status
        Status enumStatus = Status.valueOf(status.toUpperCase());
    
        HotelDto hotelDto = new HotelDto()
                .setName(name)
                .setEmplacement(emplacement)
                .setEvaluation(evaluation)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .setDescription(description)
                .setCommentaires(commentaires)
                .setNotifications(notifications)
                .setStatus(enumStatus)
                .setSubscribe_id(subscribe_id);
    
        HotelDto createdHotel = hotelService.createHotel(hotelDto, photo);
        if (createdHotel != null && createdHotel.getImageUrl() != null) {
            String imageUrl = "http://192.168.100.4:9001/" + createdHotel.getImageUrl();
            createdHotel.setImageUrl(imageUrl);
        }
    
        return ResponseEntity.ok(createdHotel);
    }
    
    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        // Fetch all hotels from the service layer
        List<HotelDto> hotels = hotelService.getAllHotels();
    
        // Set full image URL for each hotel
        hotels.forEach(hotel -> {
            if (hotel.getImageUrl() != null && !hotel.getImageUrl().startsWith("http")) {
                hotel.setImageUrl("http://localhost:9001/" + hotel.getImageUrl());
            }
        });
    
        // Return the list of hotels
        return ResponseEntity.ok(hotels);
    }
    
    
    @PutMapping("/{id}/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateHotelStatus(@PathVariable Long id, @PathVariable String status) {
        try {
            System.out.println("Attempting to update hotel with ID: " + id + " to status: " + status);
    
            Optional<Hotel> optionalHotel = hotelService.getHotelById(id);
    
            if (optionalHotel.isPresent()) {
                System.out.println("Hotel found with ID: " + id);
                Hotel hotel = optionalHotel.get();
    
                if ("accepted".equalsIgnoreCase(status) || "rejected".equalsIgnoreCase(status) || "attend".equalsIgnoreCase(status)) {
                    System.out.println("Updating status to: " + status.toUpperCase());
                    hotel.setStatus(Status.valueOf(status.toUpperCase())); // Update status
                    hotelService.save(hotel); // Save the updated entity
                    System.out.println("Status updated successfully to " + status);
                    return ResponseEntity.ok("Hotel status updated to " + status);
                } else {
                    System.out.println("Invalid status value: " + status);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status value");
                }
            } else {
                System.out.println("Hotel not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel not found");
            }
        } catch (Exception e) {
            System.err.println("Error updating status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status: " + e.getMessage());
        }
    }
    
    
    @GetMapping("/list")
    public ResponseEntity<List<HotelDto>> listHotels(@RequestParam(value = "search", required = false) String search) {
        List<HotelDto> hotels = hotelService.getAllHotels();
    
        // Filter hotels based on the search query
        if (search != null && !search.isEmpty()) {
            hotels = hotels.stream()
                    .filter(hotel -> hotel.getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }
    
        // Add base URL to image URLs and include subscribe_id
        hotels.forEach(hotel -> {
            if (hotel.getImageUrl() != null && !hotel.getImageUrl().startsWith("http")) {
                hotel.setImageUrl("http://localhost:9001/" + hotel.getImageUrl());
            }
        });
    
        return ResponseEntity.ok(hotels);
    }
    
    

    // Obtenir un hôtel par ID
    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        Optional<HotelDto> hotel = hotelService.getHotelEntityById(id);
    
        if (hotel.isPresent()) {
            return ResponseEntity.ok(hotel.get());
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
            String imageUrl = "http://localhost:9001/" + updatedHotel.getImageUrl();
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