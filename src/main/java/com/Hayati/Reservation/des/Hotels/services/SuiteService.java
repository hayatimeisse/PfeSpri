package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.SuiteDto;
import com.Hayati.Reservation.des.Hotels.entity.Suite;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;
import com.Hayati.Reservation.des.Hotels.repositoriy.SuiteRepositoriy;
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
public class SuiteService {

    private final String IMAGE_UPLOAD_DIR = "C:/Pfe/Reservation_hotels/";
    private final String BASE_IMAGE_URL = "http://192.168.100.108:9001/";

  

    @Autowired
    private HotelRepositoriy hotelRepositoriy;
    private final SuiteRepositoriy suiteRepositoriy;

    @Autowired
    public SuiteService(SuiteRepositoriy suiteRepositoriy) {
        this.suiteRepositoriy = suiteRepositoriy; // Initialize suiteRepositoriy
    }
    // public List<SuiteDto> getSuitesByHotelAndSubscribe(Long hotelId, Long subscribeId) {
    //     List<Suite> suites = SuiteRepositoriy.findByHotelIdAndSubscribeId(hotelId, subscribeId);
    //     return suites.stream()
    //                  .map(suite -> new SuiteDto(suite.getId_sui(), suite.getDescription(), suite.isDisponibilites(), suite.getImageUrl(), suite.getPrixJour()))
    //                  .collect(Collectors.toList());
    // }
    

    public List<Suite> getSuitesByHotelAndSubscribe(Long hotelId, Long subscribeId) {
        return suiteRepositoriy.findByHotelIdAndSubscribeId(hotelId, subscribeId);
    }
    // public List<SuiteDto> getSuitesForHotel(Long hotelId) {
    //     // Fetch suites for a hotel based on hotelId
    //     List<Suite> suites = suiteRepositoriy.findByHotel_IdHot(hotelId);
    
    //     // Convert suites to DTO
    //     return suites.stream().map(suite -> {
    //         SuiteDto suiteDto = new SuiteDto();
    //         suiteDto.setId_sui(suite.getId_sui());
    //         suiteDto.setPrixJour(suite.getPrixJour());
    //         suiteDto.setDisponibilites(suite.isDisponibilites());
    //         suiteDto.setDescription(suite.getDescription());
    
    //         // Associate the hotel ID to the DTO
    //         suiteDto.setHotel_id(suite.getHotel() != null ? suite.getHotel().getId_hot() : null);
    
    //         // Format image URL correctly
    //         String imageUrl = suite.getImageUrl();
    //         if (imageUrl != null && !imageUrl.startsWith("http")) {
    //             imageUrl = BASE_IMAGE_URL + imageUrl;
    //         }
    //         suiteDto.setImageUrl(imageUrl);
            
    //         return suiteDto;
    //     }).collect(Collectors.toList());
    // }
    
    
    // Créer une nouvelle suite avec téléchargement d'image et association à un hôtel
    public SuiteDto createSuite(SuiteDto suiteDto, MultipartFile photo) {
        String imageUrl = saveImage(photo, "suite_photos/");

        Suite suite = new Suite();
        suite.setPrixJour(suiteDto.getPrixJour());
        suite.setDisponibilites(suiteDto.isDisponibilites());
        suite.setDescription(suiteDto.getDescription());
        suite.setImageUrl(imageUrl);

        // Vérifiez que l'ID de l'hôtel est fourni et associez l'hôtel à la suite
        if (suiteDto.getHotel_id() != null) {
            Optional<Hotel> hotel = hotelRepositoriy.findById(suiteDto.getHotel_id());
            hotel.ifPresent(suite::setHotel);
        }

        Suite savedSuite = suiteRepositoriy.save(suite);
        return mapToDto(savedSuite);
    }

    // Obtenir la liste de toutes les suites
    public List<SuiteDto> getAllSuites() {
        return suiteRepositoriy.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Obtenir une suite par ID
    public Optional<SuiteDto> getSuiteById(Long id) {
        return suiteRepositoriy.findById(id)
                .map(this::mapToDto);
    }

    // Mise à jour d'une suite avec gestion de la photo et association à un hôtel
    public SuiteDto updateSuite(Long id, SuiteDto suiteDto, MultipartFile photo) {
        Optional<Suite> existingSuiteOptional = suiteRepositoriy.findById(id);
        if (existingSuiteOptional.isPresent()) {
            Suite existingSuite = existingSuiteOptional.get();

            if (photo != null && !photo.isEmpty()) {
                String photoPath = saveImage(photo, "suite_photos/");
                existingSuite.setImageUrl(photoPath);
            }

            existingSuite.setPrixJour(suiteDto.getPrixJour());
            existingSuite.setDisponibilites(suiteDto.isDisponibilites());
            existingSuite.setDescription(suiteDto.getDescription());

            // Mise à jour de l'hôtel associé, si hotel_id est fourni
            if (suiteDto.getHotel_id() != null) {
                Optional<Hotel> hotel = hotelRepositoriy.findById(suiteDto.getHotel_id());
                hotel.ifPresent(existingSuite::setHotel);
            }

            Suite updatedSuite = suiteRepositoriy.save(existingSuite);
            return mapToDto(updatedSuite);
        } else {
            throw new RuntimeException("Suite not found with id: " + id);
        }
    }

    // Suppression d'une suite
    public void deleteSuite(Long id) {
        Suite suite = suiteRepositoriy.findById(id)
                .orElseThrow(() -> new RuntimeException("Suite not found with id: " + id));
        suiteRepositoriy.delete(suite);
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
    
  
    // Mapper une entité Suite vers un DTO SuiteDto
    private SuiteDto mapToDto(Suite suite) {
        SuiteDto suiteDto = new SuiteDto();
        suiteDto.setId_sui(suite.getId_sui());
        suiteDto.setPrixJour(suite.getPrixJour());
        suiteDto.setDisponibilites(suite.isDisponibilites());
        suiteDto.setDescription(suite.getDescription());

        // Associe l'ID de l'hôtel à la DTO
        suiteDto.setHotel_id(suite.getHotel() != null ? suite.getHotel().getId_hot() : null);

        // Formatage correct de l'URL de l'image
        String imageUrl = suite.getImageUrl();
        if (imageUrl != null && !imageUrl.startsWith("http")) {
            imageUrl = BASE_IMAGE_URL + imageUrl;
        }
        suiteDto.setImageUrl(imageUrl);

        return suiteDto;
    }

    // Statistiques pour les suites
    public long getSuiteCount() {
        return suiteRepositoriy.count();
    }
}
