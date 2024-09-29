package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.LoginUserDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterUserDto;
import com.Hayati.Reservation.des.Hotels.dto.UserProfileUpdateRequest;
import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.responses.ChangePasswordRequest;
import com.Hayati.Reservation.des.Hotels.services.AuthenticationService;
import com.Hayati.Reservation.des.Hotels.services.JwtService;
import com.Hayati.Reservation.des.Hotels.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/auth")
@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;  // Ajout de UserService

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService, UserService userService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.userService = userService;  // Injection de UserService
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            String jwtToken = authenticationService.authenticate(loginUserDto);
            User user = authenticationService.findByEmailOrPhone(loginUserDto.getEmailOrnumerodetelephone());

            if (user != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("token", jwtToken);
                response.put("expiresIn", jwtService.getExpirationTime());
                response.put("username", user.getName());
                response.put("email", user.getEmail());
                response.put("phone", user.getNumerodetelephone());
                response.put("profileImageUrl", user.getImageUrl());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials or user not found.");
        }
    }

    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileUpdateRequest request, @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
        try {
            System.out.println("Authenticated user ID: " + user.getId_user());
            userService.updateUserProfile(user.getId_user(), request.getUsername(), request.getEmailOrPhone());
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update profile: " + e.getMessage());
        }
    }
    
    
    

    
    

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
            }

            String token = changePasswordRequest.getToken();
            if (jwtService.isTokenExpired(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
            }

            User user = authenticationService.findUserByToken(token);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            authenticationService.changePassword(user, changePasswordRequest.getNewPassword());
            return ResponseEntity.ok("Password successfully changed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error changing password: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
public ResponseEntity<?> register(@RequestPart("user") String registerUserDtoJson,
                                  @RequestPart("photo") MultipartFile photo) {
    try {
        // Vérification si l'image est vide
        if (photo.isEmpty()) {
            return ResponseEntity.badRequest().body("Le fichier photo est vide.");
        }

        // Mapper les données JSON de l'utilisateur en objet DTO
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterUserDto registerUserDto = objectMapper.readValue(registerUserDtoJson, RegisterUserDto.class);

        // Enregistrement de l'utilisateur et sauvegarde de l'image via le service d'authentification
        // Appel de la méthode signUp avec RegisterUserDto et MultipartFile (photo)
        User registeredUser = authenticationService.signUp(registerUserDto, photo);

        // Réponse réussie avec l'utilisateur enregistré
        return ResponseEntity.ok(registeredUser);

    } catch (IOException e) {
        // Gestion des erreurs liées à l'upload du fichier
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec du téléchargement de l'image : " + e.getMessage());
    } catch (RuntimeException e) {
        // Gestion d'autres erreurs liées à l'enregistrement ou aux validations
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'enregistrement : " + e.getMessage());
    }
}

}
