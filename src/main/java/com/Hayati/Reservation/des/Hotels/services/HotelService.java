package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;
import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.enumeration.Status;
import com.Hayati.Reservation.des.Hotels.exceptions.ResourceNotFoundException;
import com.Hayati.Reservation.des.Hotels.repositoriy.HotelRepositoriy;
import com.Hayati.Reservation.des.Hotels.repositoriy.SubscribeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final String BASE_IMAGE_URL = "http://192.168.100.174:9001/";

    private final SubscribeRepository subscribeRepository;
    private final HotelRepositoriy hotelRepository;

    @Autowired
    public HotelService(SubscribeRepository subscribeRepository, HotelRepositoriy hotelRepository) {
        this.subscribeRepository = subscribeRepository;
        this.hotelRepository = hotelRepository;
    }

    // Retrieve list of all hotels
    // public List<HotelDto> getAllHotels() {
    //     return hotelRepository.findAll().stream()
    //             .map(this::mapToDto)
    //             .collect(Collectors.toList());
    // }

    // Get a single hotel by ID
    public Optional<HotelDto> getHotelEntityById(Long id) {
        return hotelRepository.findById(id)
                .map(this::mapToDto);
    }

    // Get hotels by Subscribe ID
    public List<HotelDto> getHotelsBySubscribeId(Long subscribeId) {
        List<Hotel> hotels = hotelRepository.findBySubscribeId(subscribeId); // Ensure this method is correctly filtering by subscribeId
        return hotels.stream().map(this::mapToDto).collect(Collectors.toList());
    }
    public List<HotelDto> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream().map(hotel -> {
            HotelDto hotelDto = new HotelDto()
                .setId_hot(hotel.getId_hot())
                .setName(hotel.getName())
                .setEmplacement(hotel.getEmplacement())
                .setEvaluation(hotel.getEvaluation())
                .setLatitude(hotel.getLatitude())
                .setLongitude(hotel.getLongitude())
                .setDescription(hotel.getDescription())
                .setCommentaires(hotel.getCommentaires())
                .setNotifications(hotel.getNotifications())
                .setImageUrl(hotel.getImageUrl())
                .setStatus(hotel.getStatus());
    
            // Map subscribe_id if available
            if (hotel.getSubscribe() != null) {
                hotelDto.setSubscribe_id(hotel.getSubscribe().getId());
            }
            return hotelDto;
        }).collect(Collectors.toList());
    }
    
    // Delete a hotel by ID
    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        hotelRepository.delete(hotel);
    }

    // Create a new hotel
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
        hotel.setDescription(hotelDto.getDescription());
        hotel.setStatus(hotelDto.getStatus() != null ? hotelDto.getStatus() : Status.ATTEND);
        hotel.setImageUrl(imageUrl);

        if (hotelDto.getSubscribe_id() != null) {
            Optional<Subscribe> subscribe = subscribeRepository.findById(hotelDto.getSubscribe_id());
            subscribe.ifPresent(hotel::setSubscribe); // Set Subscribe entity
        }

        Hotel savedHotel = hotelRepository.save(hotel);

        return mapToDto(savedHotel);
    }

    public Optional<Hotel> getHotelById(Long id) {
    return hotelRepository.findById(id);
}

    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
    // Update an existing hotel
    public HotelDto updateHotel(Long id, HotelDto hotelDto, MultipartFile photo) {
        Optional<Hotel> existingHotelOptional = hotelRepository.findById(id);
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
            existingHotel.setStatus(hotelDto.getStatus());

            Hotel updatedHotel = hotelRepository.save(existingHotel);
            return mapToDto(updatedHotel);
        } else {
            throw new ResourceNotFoundException("Hôtel non trouvé");
        }
    }

    // Helper method to save images
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

    // Helper method to map Hotel entity to HotelDto
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
        hotelDto.setStatus(hotel.getStatus());

        String imageUrl = hotel.getImageUrl();
        if (imageUrl != null && !imageUrl.startsWith("http")) {
            imageUrl = BASE_IMAGE_URL + imageUrl;
        }
        hotelDto.setImageUrl(imageUrl);

        return hotelDto;
    }

    // Hotel count for statistics
    public long getHotelCount() {
        return hotelRepository.count();
    }
}
