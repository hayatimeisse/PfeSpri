package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.*;
import com.Hayati.Reservation.des.Hotels.entity.Client;
import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.enumeration.Status;
import com.Hayati.Reservation.des.Hotels.responses.ChangePasswordRequest;
import com.Hayati.Reservation.des.Hotels.responses.LoginResponse;
import com.Hayati.Reservation.des.Hotels.services.AuthenticationService;
import com.Hayati.Reservation.des.Hotels.services.ClientService;
import com.Hayati.Reservation.des.Hotels.services.EmailService;
import com.Hayati.Reservation.des.Hotels.services.JwtService;
import com.Hayati.Reservation.des.Hotels.services.SubscribeService;

import io.jsonwebtoken.io.IOException;

import com.Hayati.Reservation.des.Hotels.repositoriy.ClientRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.SubscribeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
  private final SubscribeService subscribeService;
  private final ClientService clientService;
  private final EmailService emailService;

    public AuthenticationController(JwtService jwtService, SubscribeService subscribeService,AuthenticationService authenticationService,EmailService emailService,ClientService clientService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.subscribeService =subscribeService;
        this.emailService=emailService;
        this.clientService=clientService;
    }

    @Autowired
    private ClientRepository clientRepository;
    @PostMapping("/signup/client")
    public ResponseEntity<?> signupClient(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("nom") String nom,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("numerodetelephone") String numerodetelephone) {

        if (photo == null || photo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Photo is required");
        }

        RegisterClientDto registerClientDto = new RegisterClientDto()
                .setEmail(email)
                .setPassword(password)
                .setNom(nom)
                .setPhoto(photo)
                .setNumerodetelephone(numerodetelephone);

        Client createdClient = authenticationService.signupClient(registerClientDto);
        if (createdClient != null && createdClient.getPhoto() != null) {
            String imageUrl = "http://localhost:9001/client_photos" + createdClient.getPhoto();
            createdClient.setPhoto(imageUrl);
        }

        return ResponseEntity.ok(createdClient);
    }

    
    @PostMapping("/signup/admin")
    public ResponseEntity<User> registerAdmin(@RequestBody RegisterAdminDto registerAdminDto) {
        User registeredAdmin = authenticationService.signupAdmin(registerAdminDto);
        return ResponseEntity.ok(registeredAdmin);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        LoginResponse response = authenticationService.authenticate(loginUserDto);
        return ResponseEntity.ok(response);
    }
  
   
   
    @PostMapping("/signup/subscribe")
    public ResponseEntity<?> signupSubscribe(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("nom") String nom) {
    
        try {
            // Créer un nouvel utilisateur
            Subscribe subscribe = new Subscribe();
            subscribe.setEmail(email);
            subscribe.setPassword(password);
            subscribe.setName(nom);
            subscribe.setStatus(Status.ATTEND);
           // subscribe.setVerificationCode("RQTER");
                     // Statut initial en attente
             
            // Sauvegarder l'utilisateur dans la base de données
            subscribe = subscribeRepository.save(subscribe);
    
            // Envoyer un email de vérification
            System.out.println(subscribe);
           emailService.sendVerificationEmail(subscribe);
    
            return ResponseEntity.ok("Inscription réussie. Veuillez vérifier votre email.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite : " + e.getMessage());
        }
    }
    
    


    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(
            @RequestParam("userId") Long userId,
            @RequestParam("code") String code) {
        try {
            boolean isVerified = authenticationService.verifyEmail(userId, code);
            if (isVerified) {
                return ResponseEntity.ok("Email vérifié avec succès.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Code de vérification invalide.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite : " + e.getMessage());
        }
    }
    
    
    
    
    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(
            @RequestHeader("Authorization") String token,
            @RequestParam("email") String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
    
        String extractedToken = token.substring(7); // Remove 'Bearer ' prefix
        Subscribe user = (Subscribe) authenticationService.getUserFromToken(extractedToken);
    
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    
        UpdateSubscribeDto updateDto = new UpdateSubscribeDto();
        updateDto.setEmail(email);
        if (password != null) {
            updateDto.setPassword(password);
        }
    
        Subscribe updatedUser = subscribeService.updateSubscribeProfile(user.getId(), updateDto, photo);
      
        return ResponseEntity.ok(updatedUser); // Returns the updated user profile data
    }
    @PutMapping("/update-client-profile")
    public ResponseEntity<?> updateClientProfile(
            @RequestHeader("Authorization") String token,
            @RequestParam("email") String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {

        try {
            // Extraire le token sans le préfixe "Bearer "
            String extractedToken = token.substring(7);

            // Récupérer le client à partir du token
            Client client = (Client) authenticationService.getUserFromToken(extractedToken);

            if (client == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Client not found");
            }

            // Créer un DTO pour les mises à jour
            UpdateClientDto updateDto = new UpdateClientDto();
            updateDto.setEmail(email);
            if (password != null && !password.isEmpty()) {
                updateDto.setPassword(password);
            }

            // Appeler le service pour mettre à jour le profil
            Client updatedClient = clientService.updateClientProfile(client.getId(), updateDto, photo);

            // Mettre à jour l'URL complète de la photo si elle est présente
            if (updatedClient.getPhoto() != null) {
                String imageUrl = "http://192.168.100.174:9001/client_photos/" + updatedClient.getPhoto();
                updatedClient.setPhoto(imageUrl);
            }

            // Retourner les données mises à jour
            return ResponseEntity.ok(updatedClient);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        String extractedToken = token.substring(7); // Remove 'Bearer ' prefix
        Subscribe user = (Subscribe) authenticationService.getUserFromToken(extractedToken);
    
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    
        // // Generate the full URL for the photo
        // if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
        //     String imageUrl = "http://localhost:9001/" + user.getPhoto(); // Update the base URL if necessary
        //     user.setPhoto(imageUrl);
        // }
    
        // Create a new UserProfileDto and set the photo URL
        UserProfileDto profileDto = new UserProfileDto(user);
        // profileDto.setPhoto(user.getPhoto()); // Set the full URL for the photo
    
        return ResponseEntity.ok(profileDto);
    }
    
    
    
    
    @GetMapping("/client-profile")
public ResponseEntity<?> getClientProfile(@RequestHeader("Authorization") String token) {
    String extractedToken = token.substring(7); // Remove 'Bearer ' prefix
    Client user = (Client) authenticationService.getUserFromToken(extractedToken);

    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
    }

    // Map Client to DTO
    UserProfileClientDto profileDto = new UserProfileClientDto(user);

    return ResponseEntity.ok(profileDto); // Return profile DTO
}

    



    // @PutMapping("/update-profile")
    // public ResponseEntity<?> updateProfile(
    //         @RequestHeader("Authorization") String token,
    //         @RequestParam("email") String email,
    //         @RequestParam(value = "password", required = false) String password,
    //         @RequestParam(value = "photo", required = false) MultipartFile photo) {

    //     String extractedToken = token.substring(7); // Remove 'Bearer ' prefix
    //     Subscribe user = (Subscribe) authenticationService.getUserFromToken(extractedToken);

    //     if (user == null) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
    //     }

    //     UpdateSubscribeDto updateDto = new UpdateSubscribeDto();
    //     updateDto.setEmail(email);
    //     updateDto.setPassword(password);

    //     Subscribe updatedUser = subscribeService.updateSubscribeProfile(user.getId(), updateDto, photo);

    //     return ResponseEntity.ok(updatedUser);
    // }
    @Autowired
    private SubscribeRepository subscribeRepository;


    
 @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordRequest changePasswordRequest) {

        // Extraire le token si besoin (le supprimer du préfixe 'Bearer ')
        String extractedToken = token.substring(7); // Enlever 'Bearer ' du token

        // Récupérer l'utilisateur depuis le token
        User user = authenticationService.getUserFromToken(extractedToken);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non trouvé");
        }

        // Vérifier que l'ancien mot de passe est correct
        boolean isOldPasswordValid = authenticationService.checkPassword(user, changePasswordRequest.getOldPassword());

        if (!isOldPasswordValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'ancien mot de passe est incorrect");
        }

        // Vérifier que le nouveau mot de passe et la confirmation correspondent
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Les mots de passe ne correspondent pas");
        }

        // Changer le mot de passe
        authenticationService.updatePassword(user, changePasswordRequest.getNewPassword());

        return ResponseEntity.ok("Mot de passe changé avec succès");
    }

}
