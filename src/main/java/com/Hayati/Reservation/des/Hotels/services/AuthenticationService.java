package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.RegisterClientDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterSubscribeDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterAdminDto;
import com.Hayati.Reservation.des.Hotels.dto.LoginUserDto;
import com.Hayati.Reservation.des.Hotels.entity.Admin;
import com.Hayati.Reservation.des.Hotels.entity.Client;
import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.enumeration.Status;
import com.Hayati.Reservation.des.Hotels.repositoriy.ClientRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.SubscribeRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.UserRepository;
import com.Hayati.Reservation.des.Hotels.responses.LoginResponse;

import io.jsonwebtoken.Claims;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SubscribeRepository subscribeRepository;

    private final String CLIENT_IMAGE_UPLOAD_DIR = "C:/Pfe/Reservation_client/";
    private final String SUBSCRIBE_IMAGE_UPLOAD_DIR = "C:/Pfe/Reservation_Subscribe/";

    public AuthenticationService(UserRepository userRepository,
                                 AuthenticationManager authenticationManager,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    @Autowired
    private EmailService emailService;

    public Client signupClient(RegisterClientDto input) {
        // Create a new client
        Client client = new Client();
        client.setName(input.getNom());
        client.setEmail(input.getEmail());
        client.setPassword(passwordEncoder.encode(input.getPassword()));
        client.setNumerodetelephone(input.getNumerodetelephone());
        User savedUser = userRepository.save(client);
        client.setId(savedUser.getId());

      

        client.getRoles().add("ROLE_CLIENT");
        return clientRepository.save(client);
    }

    public Subscribe signupSubscribe(RegisterSubscribeDto input) {
        Subscribe subscribe = new Subscribe();
        subscribe.setName(input.getNom());
        subscribe.setEmail(input.getEmail());
        subscribe.setPassword(passwordEncoder.encode(input.getPassword()));
    
        // Save user entity first to generate an ID
        User savedUser = userRepository.save(subscribe);
        subscribe.setId(savedUser.getId());
    
        // Handle photo upload
        // if (input.getPhoto() != null && !input.getPhoto().isEmpty()) {
        //     String photoPath = saveImage(input.getPhoto(), SUBSCRIBE_IMAGE_UPLOAD_DIR);
        //     subscribe.setPhoto(photoPath);
        // } else {
        //     throw new RuntimeException("Une photo est requise pour l'inscription");
        // }
    
        // Add role
        subscribe.getRoles().add("ROLE_SUBSCRIBE");
    
        // Save the subscribe user
        Subscribe savedSubscribe = subscribeRepository.save(subscribe);
    
        // Append the base URL to the photo field for display
        // String baseUrl = "http://localhost:9001/";
        // savedSubscribe.setPhoto(baseUrl + savedSubscribe.getPhoto());
    
        return savedSubscribe;
    }
    
    private String saveImage(MultipartFile photo, String uploadDir) {
        try {
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, photo.getBytes());
            return fileName; // Return only the file name
        } catch (IOException e) {
            throw new RuntimeException("Échec de l'enregistrement de l'image", e);
        }
    }
    public boolean verifyEmail(Long userId, String code) {
        Subscribe subscribe = subscribeRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé."));
        
        // Vérifiez si le code est null
        if (subscribe.getVerificationCode() == null) {
            throw new IllegalArgumentException("Le code de vérification est introuvable.");
        }

        if (subscribe.getVerificationCode().equals(code)) {
            subscribe.setStatus(Status.ACCEPTED); // Activer l'utilisateur
           
            subscribeRepository.save(subscribe);
            return true;
        }

        return false; // Code invalide
    }

    public User signupAdmin(RegisterAdminDto input) {
        var admin = new Admin();
        admin.setName(input.getNom());
        admin.setEmail(input.getEmail());
        admin.setPassword(passwordEncoder.encode(input.getPassword()));
        admin.getRoles().add("ROLE_ADMIN");
        return userRepository.save(admin);
    }

    public LoginResponse authenticate(LoginUserDto input) {
        // Authenticate user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmailOrnumerodetelephone(), input.getPassword()));
        User user = userRepository.findByEmail(input.getEmailOrnumerodetelephone()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
    
        // Prepare login response
        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);
        response.setExpiresIn(jwtService.getExpirationTime());
    
        // Add user-specific data
        if (user instanceof Subscribe) {
            response.setUserType("Subscribe");
            Subscribe subscribe = (Subscribe) user;
    
            // Ensure the photo URL is correct
            // if (subscribe.getPhoto() != null && !subscribe.getPhoto().startsWith("http")) {
            //     String baseUrl = "http://192.168.100.4:9001/subscribe_photos/";
            //     subscribe.setPhoto(baseUrl + subscribe.getPhoto());
            // }
    
            response.setUserData(subscribe);
        } else if (user instanceof Client) {
            response.setUserType("Client");
            response.setUserData(user);
        } else if (user instanceof Admin) {
            response.setUserType("Admin");
            response.setUserData(user);
        } else {
            response.setUserType("User");
            response.setUserData(user);
        }
    
        return response;
    }
    
      public User getUserFromToken(String token) {
        // Extract user from token using JWT claims
        Claims claims = jwtService.extractAllClaims(token);
        String email = claims.getSubject(); // Assume subject is the user's email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Method to check if the old password is valid
    public boolean checkPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword()); // Compare old password
    }

    // Method to update the password
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user); // Save the updated user
    }

    public void updateUserProfile(User user) {
        // Save the updated user in the repository
        userRepository.save(user);
    }
}