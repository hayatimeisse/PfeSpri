package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.*;
import com.Hayati.Reservation.des.Hotels.entity.Client;
import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.enumeration.Status;
import com.Hayati.Reservation.des.Hotels.responses.ChangePasswordRequest;
import com.Hayati.Reservation.des.Hotels.responses.LoginResponse;
import com.Hayati.Reservation.des.Hotels.services.AuthenticationService;
import com.Hayati.Reservation.des.Hotels.services.JwtService;
import com.Hayati.Reservation.des.Hotels.services.SubscribeService;
import com.Hayati.Reservation.des.Hotels.repositoriy.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
  private final SubscribeService subscribeService;

    public AuthenticationController(JwtService jwtService, SubscribeService subscribeService,AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.subscribeService =subscribeService;
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
            String imageUrl = "http://localhost:9001/" + createdClient.getPhoto();
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
            @RequestParam("nom") String nom,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("status") Status status) {

        if (photo == null || photo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Photo is required");
        }

        RegisterSubscribeDto registerSubscribeDto = new RegisterSubscribeDto()
                .setEmail(email)
                .setPassword(password)
                .setNom(nom)
                .setPhoto(photo);

        Subscribe createdSubscribe = subscribeService.createSubscribe(registerSubscribeDto);
        createdSubscribe.setStatus(status); // Set the status based on the request parameter

        // Update photo URL if provided
        if (createdSubscribe.getPhoto() != null) {
            String imageUrl = "http://192.168.100.4:9001/subscribe_photos/" + createdSubscribe.getPhoto();
            createdSubscribe.setPhoto(imageUrl);
        }

        return ResponseEntity.ok(createdSubscribe);
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
        
        // Set the complete image URL if a photo was uploaded
        if (updatedUser.getPhoto() != null) {
            String imageUrl = "http://192.168.100.174:9001/subscribe_photos/" + updatedUser.getPhoto();
            updatedUser.setPhoto(imageUrl);
        }
    
        return ResponseEntity.ok(updatedUser); // Returns the updated user profile data
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        String extractedToken = token.substring(7); // Remove 'Bearer ' prefix
        Subscribe user = (Subscribe) authenticationService.getUserFromToken(extractedToken);
    
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    
        // Generate the full URL for the photo
        if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
            String imageUrl = "http://localhost:9001/subscribe_photos/" + user.getPhoto(); // Update the base URL if necessary
            user.setPhoto(imageUrl);
        }
    
        // Create a new UserProfileDto and set the photo URL
        UserProfileDto profileDto = new UserProfileDto(user);
        profileDto.setPhoto(user.getPhoto()); // Set the full URL for the photo
    
        return ResponseEntity.ok(profileDto);
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
    @PostMapping("/verify")
public ResponseEntity<?> verifyEmail(@RequestParam("code") String verificationCode) {
    Optional<Client> client = clientRepository.findByVerificationCode(verificationCode);

    if (client.isPresent()) {
        Client existingClient = client.get();
        existingClient.setIsEmailVerified(true);
        existingClient.setVerificationCode(null); // Clear the code after verification
        clientRepository.save(existingClient);
        return ResponseEntity.ok("Email verified successfully!");
    } else {
        return ResponseEntity.badRequest().body("Invalid verification code.");
    }
}

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
