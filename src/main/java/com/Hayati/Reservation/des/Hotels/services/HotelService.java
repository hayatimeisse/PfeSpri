package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.entity.Chambre;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;
import com.Hayati.Reservation.des.Hotels.exceptions.ResourceNotFoundException;
import com.Hayati.Reservation.des.Hotels.repositoriy.HotelRepositoriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final String IMAGE_UPLOAD_DIR = "C:/Pfe/Reservation_hotels/";
    private final String BASE_IMAGE_URL = "http://192.168.100.61:9001/";
    
    @Autowired
    private HotelRepositoriy hotelRepositoriy;

    // Créer un nouvel hôtel avec téléchargement d'image
    public HotelDto createHotel(HotelDto hotelDto, MultipartFile photo) {
        String imageUrl = saveImage(photo, "hotel_photos/");

        Hotel hotel = new Hotel();
        hotel.setName(hotelDto.getName());
        hotel.setEmplacement(hotelDto.getEmplacement());
        hotel.setEvaluation(hotelDto.getEvaluation());
        hotel.setLatitude(hotelDto.getLatitude());
        hotel.setLongitude(hotelDto.getLongitude());
        hotel.setCommentaires(hotelDto.getCommentaires());
        hotel.setNotifications(hotelDto.getNotifications());
        hotel.setImageUrl(imageUrl);
        hotel.setDescription(hotelDto.getDescription());

        Hotel savedHotel = hotelRepositoriy.save(hotel);

        return mapToDto(savedHotel);
    }

    // Obtenir la liste de tous les hôtels
    public List<HotelDto> getAllHotels() {
        return hotelRepositoriy.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Obtenir un hôtel par ID
    public Optional<HotelDto> getHotelById(Long id) {
        return hotelRepositoriy.findById(id)
                .map(this::mapToDto);
    }

    // Mise à jour d'un hôtel avec gestion de la photo
   public HotelDto updateHotel(Long id, HotelDto hotelDto, MultipartFile photo) {


     Optional<Hotel> existingHotelOptional = hotelRepositoriy.findById(id);
        if (existingHotelOptional.isPresent()) {
            Hotel existingHotel = existingHotelOptional.get();

            if (photo != null && !photo.isEmpty()) {
                String photoPath = saveImage(photo, "hotel_photos/");
                existingHotel.setImageUrl(photoPath);
            }
           

    existingHotel.setName(hotelDto.getName());
    existingHotel.setEmplacement(hotelDto.getEmplacement());
    existingHotel.setEvaluation(hotelDto.getEvaluation());
    existingHotel.setLatitude(hotelDto.getLatitude());
    existingHotel.setLongitude(hotelDto.getLongitude());
    existingHotel.setCommentaires(hotelDto.getCommentaires());
    existingHotel.setNotifications(hotelDto.getNotifications());
    existingHotel.setDescription(hotelDto.getDescription());
    

            Hotel updatedHotel = hotelRepositoriy.save(existingHotel);
            return mapToDto(updatedHotel);
    } else {
        throw new ResourceNotFoundException("Hôtel non trouvé");
    }
}

    // Suppression d'un hôtel
    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepositoriy.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        hotelRepositoriy.delete(hotel);
    }

    // Fonction pour enregistrer l'image sur le serveur
    private String saveImage(MultipartFile photo, String subDir) {
        try {
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            Path path = Paths.get(IMAGE_UPLOAD_DIR + subDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, photo.getBytes());
            return subDir + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }

    private HotelDto mapToDto(Hotel hotel) {
        HotelDto hotelDto = new HotelDto();
        hotelDto.setId_hot(hotel.getId_hot());
        hotelDto.setName(hotel.getName());
        hotelDto.setEmplacement(hotel.getEmplacement());
        hotelDto.setEvaluation(hotel.getEvaluation());
        hotelDto.setLatitude(hotel.getLatitude());
        hotelDto.setLongitude(hotel.getLongitude());
        hotelDto.setCommentaires(hotel.getCommentaires());
        hotelDto.setNotifications(hotel.getNotifications());
        hotelDto.setDescription(hotel.getDescription());
        
        // Ensure correct URL formatting for the image URL
        String imageUrl = hotel.getImageUrl();
        if (imageUrl != null && !imageUrl.startsWith("http")) {
            imageUrl = BASE_IMAGE_URL + imageUrl;
        }
        hotelDto.setImageUrl(imageUrl);
        
        return hotelDto;
    }
    
    

    //// Statistiques pour les hôtels
    public long getHotelCount() {
        return hotelRepositoriy.count();
    }
}
