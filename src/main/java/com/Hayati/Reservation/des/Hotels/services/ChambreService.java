package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.ChambreDto;
import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.entity.Chambre;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;
import com.Hayati.Reservation.des.Hotels.entity.Suite;
import com.Hayati.Reservation.des.Hotels.repositoriy.ChambreRepositoriy;
import com.Hayati.Reservation.des.Hotels.repositoriy.SuiteRepositoriy;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChambreService {

    private final String IMAGE_UPLOAD_DIR = "C:/Pfe/Reservation_hotels/";
    private final String BASE_IMAGE_URL = "http://192.168.100.108:9001/";

    private final ChambreRepositoriy chambreRepositoriy;
   
    @Autowired
    public ChambreService(ChambreRepositoriy chambreRepository) {
        this.chambreRepositoriy = chambreRepository;
    }

    public List<Chambre> getChambresBySuiteAndSubscribe(Long suiteId, Long subscribeId) {
        // Utilisation de l'instance injectée
        return chambreRepositoriy.findBySuiteIdAndSubscribeId(suiteId, subscribeId);
    }

    @Autowired
    private SuiteRepositoriy suiteRepositoriy;  
  
    // Créer une nouvelle chambre avec téléchargement d'image et association à une suite
    public ChambreDto createChambre(ChambreDto chambreDto, MultipartFile photo) {
        String imageUrl = saveImage(photo, "chambre_photos/");

        Chambre chambre = new Chambre();
        chambre.setName(chambreDto.getName());
        chambre.setCapacite(chambreDto.getCapacite());
        chambre.setPrixJour(chambreDto.getPrixJour());
        chambre.setDisponibilites(chambreDto.isDisponibilites());
        chambre.setImageUrl(imageUrl);
        chambre.setDescription(chambreDto.getDescription());

        // Vérifiez que l'ID de la suite est fourni et associez la suite à la chambre
        if (chambreDto.getSuite_id() != null) {
            Optional<Suite> suite = suiteRepositoriy.findById(chambreDto.getSuite_id());
            suite.ifPresent(chambre::setSuite);
        }
       
        
        
        Chambre savedChambre = chambreRepositoriy.save(chambre);

        return mapToDto(savedChambre);
    }
    @Transactional
    public List<Chambre> getChambresByHotelId(Long hotelId) {
        return chambreRepositoriy.findByHotelId(hotelId);
    }
    

    // public List<ChambreDto> getChambresByHotelId(Long hotelId) {
    //     // Fetch suites by hotel ID
    //     List<Suite> suites = suiteRepositoriy.findByHotel_IdHot(hotelId);
    
    //     if (suites.isEmpty()) {
    //         throw new RuntimeException("No suites found for the hotel");
    //     }
    
    //     // Extract suite IDs
    //     List<Long> suiteIds = suites.stream()
    //                                 .map(Suite::getId_sui)
    //                                 .collect(Collectors.toList());
    
    //     // Fetch chambres by suite IDs
    //     List<Chambre> chambres = chambreRepositoriy.findChambresBySuiteIds(suiteIds);
    
    //     // Map to DTOs
    //     return chambres.stream()
    //                    .map(this::mapToDto)
    //                    .collect(Collectors.toList());
    // }
    

    // Obtenir la liste de toutes les chambres
    public List<ChambreDto> getAllChambres() {
        return chambreRepositoriy.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
  
  
  
   
    // Obtenir une chambre par ID
    public Optional<ChambreDto> getChambreById(Long id) {
        return chambreRepositoriy.findById(id)
                .map(this::mapToDto);
    }
    

    public ChambreDto updateChambre(Long id, ChambreDto chambreDto, MultipartFile photo) {
        Optional<Chambre> existingChambreOptional = chambreRepositoriy.findById(id);
        if (existingChambreOptional.isPresent()) {
            Chambre existingChambre = existingChambreOptional.get();

            if (photo != null && !photo.isEmpty()) {
                String photoPath = saveImage(photo, "chambre_photos/");
                existingChambre.setImageUrl(photoPath);
            }

            existingChambre.setName(chambreDto.getName());
            existingChambre.setPrixJour(chambreDto.getPrixJour());
            existingChambre.setCapacite(chambreDto.getCapacite());
            existingChambre.setDisponibilites(chambreDto.isDisponibilites());
            existingChambre.setDescription(chambreDto.getDescription());

            Chambre updatedChambre = chambreRepositoriy.save(existingChambre);
            return mapToDto(updatedChambre);
        } else {
            throw new RuntimeException("Chambre not found with id: " + id);
        }
    }
    // Suppression d'une chambre
    public void deleteChambre(Long id) {
        Chambre chambre = chambreRepositoriy.findById(id)
                .orElseThrow(() -> new RuntimeException("Chambre not found with id: " + id));
        chambreRepositoriy.delete(chambre);
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
    private ChambreDto convertToDto(Chambre chambre) {
        ChambreDto chambreDto = new ChambreDto();
        chambreDto.setName(chambre.getName());
        chambreDto.setPrixJour(chambre.getPrixJour());
        chambreDto.setDisponibilites(chambre.isDisponibilites());
        chambreDto.setDescription(chambre.getDescription());
        // Set other fields as needed
        return chambreDto;
    }
    

    private ChambreDto mapToDto(Chambre chambre) {
        ChambreDto chambreDto = new ChambreDto();
        chambreDto.setId_cham(chambre.getId_cham());
        chambreDto.setName(chambre.getName());
        chambreDto.setCapacite(chambre.getCapacite());
        chambreDto.setPrixJour(chambre.getPrixJour());
        chambreDto.setDisponibilites(chambre.isDisponibilites());
        chambreDto.setDescription(chambre.getDescription());

        // Associe l'ID de la suite à la DTO
        chambreDto.setSuite_id(chambre.getSuite() != null ? chambre.getSuite().getId_sui() : null);

        // Formatage correct de l'URL de l'image
        String imageUrl = chambre.getImageUrl();
        if (imageUrl != null && !imageUrl.startsWith("http")) {
            imageUrl = BASE_IMAGE_URL + imageUrl;
        }
        chambreDto.setImageUrl(imageUrl);

        return chambreDto;
    }
   
    
    public long getChambreCount() {
        return chambreRepositoriy.count();
    }
}
