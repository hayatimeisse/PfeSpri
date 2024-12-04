package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.*;
import com.Hayati.Reservation.des.Hotels.entity.Admin;
import com.Hayati.Reservation.des.Hotels.entity.Client;
import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.entity.VerificationCode;
import com.Hayati.Reservation.des.Hotels.enumeration.Status;
import com.Hayati.Reservation.des.Hotels.responses.ChangePasswordRequest;
import com.Hayati.Reservation.des.Hotels.responses.LoginResponse;
import com.Hayati.Reservation.des.Hotels.services.AuthenticationService;
import com.Hayati.Reservation.des.Hotels.services.ClientService;
import com.Hayati.Reservation.des.Hotels.services.EmailService;
import com.Hayati.Reservation.des.Hotels.services.JwtService;
import com.Hayati.Reservation.des.Hotels.services.JwtTokenProvider;
import com.Hayati.Reservation.des.Hotels.services.SubscribeService;
import com.Hayati.Reservation.des.Hotels.services.VerificationCodeService;

import io.jsonwebtoken.io.IOException;

import com.Hayati.Reservation.des.Hotels.repositoriy.AdminRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.ClientRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.SubscribeRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.UserRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.VerificationCodeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
  private final SubscribeService subscribeService;
  private final ClientService clientService;
  private final EmailService emailService;
  private final VerificationCodeRepository verificationCodeRepository;
  private final VerificationCodeService verificationCodeService;
  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private AdminRepository adminRepository;
    public AuthenticationController(JwtService jwtService,VerificationCodeService verificationCodeService, SubscribeService subscribeService,AuthenticationService authenticationService,EmailService emailService,ClientService clientService,VerificationCodeRepository verificationCodeRepository) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.subscribeService =subscribeService;
        this.emailService=emailService;
        this.clientService=clientService;
        this.verificationCodeRepository=verificationCodeRepository;
        this.verificationCodeService=verificationCodeService;
    }

 
    @PostMapping("/signup/client")
    public ResponseEntity<?> signupClient(@RequestBody RegisterClientDto clientDto) {
        // Create a new Client object
        Client client = new Client();
        client.setEmail(clientDto.getEmail());
        client.setPassword(passwordEncoder.encode(clientDto.getPassword())); // Encrypt the password
        client.setName(clientDto.getNom());
        client.setEmailVerified(false); // Email verification status initially false
    
        // Save the client entity to the database
        client = clientRepository.save(client);
        client.getRoles().add("ROLE_CLIENT");
    
        // Send verification email
        emailService.sendVerificationEmail(client);
    
        // Optional: Generate and return a token for the client (similar to subscribe process)
        LoginResponse response = new LoginResponse();
        String token = jwtService.generateToken(client);
    
        response.setToken(token);
        response.setExpiresIn(jwtService.getExpirationTime());
        response.setUserType("Client");
        response.setUserData(client);
    
        return ResponseEntity.ok(response);
    }
    


    
@PostMapping("/signup/admin")
public ResponseEntity<User> registerAdmin(@RequestBody RegisterAdminDto registerAdminDto) {
    // Create the Admin object (assuming RegisterAdminDto contains necessary fields)
    Admin admin = new Admin();
    admin.setEmail(registerAdminDto.getEmail());
    admin.setPassword(passwordEncoder.encode(registerAdminDto.getPassword())); // Encrypt the password
    admin.setName(registerAdminDto.getNom());
    admin.setEmailVerified(false); // Email verification status initially false

    // Assign the role ROLE_ADMIN
    admin.getRoles().add("ROLE_ADMIN");

    // Save the admin entity to the database
    admin = adminRepository.save(admin);

    // Send verification email
    emailService.sendVerificationEmail(admin);

    // Optional: Generate and return a token for the admin (similar to other processes)
    LoginResponse response = new LoginResponse();
    String token = jwtService.generateToken(admin);

    response.setToken(token);
    response.setExpiresIn(jwtService.getExpirationTime());
    response.setUserType("Admin");
    response.setUserData(admin);

    return ResponseEntity.ok(admin);
}


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        LoginResponse response = authenticationService.authenticate(loginUserDto);
        return ResponseEntity.ok(response);
    }
  
   
   
    @Autowired
private BCryptPasswordEncoder passwordEncoder;
@PostMapping("/signup/subscribe")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> createSubscribe(
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam("nom") String nom) { // Remove status from parameters

    Subscribe subscribe = new Subscribe();
    subscribe.setEmail(email);
    subscribe.setPassword(passwordEncoder.encode(password)); // Encrypt the password
    subscribe.setName(nom);
    
    // Set status to ATTEND automatically
    subscribe.setStatus(Status.ATTEND);
    
    subscribe.setEmailVerified(false); // Email verification status initially false

    // Assign the role ROLE_SUBSCRIBE
    subscribe.getRoles().add("ROLE_SUBSCRIBE");

    // Save the subscribe entity to the database
    subscribe = subscribeRepository.save(subscribe);

    // Send verification email
    emailService.sendVerificationEmail(subscribe);

    // Optional: Generate and return a token, similar to signup/subscribe
    LoginResponse response = new LoginResponse();
    String token = jwtService.generateToken(subscribe);

    response.setToken(token);
    response.setExpiresIn(jwtService.getExpirationTime());
    response.setUserType("Subscribe");
    response.setUserData(subscribe);

    return ResponseEntity.ok(response);
}


@PostMapping("/forgot-password")
public ResponseEntity<String> forgotPassword(@RequestParam String identifier) {
    System.out.println("Demande de réinitialisation pour : " + identifier);

    if (identifier.contains("@")) {
        // Génération du code
        String resetCode = String.valueOf((int) (Math.random() * 900000) + 100000);
        System.out.println("Code généré : " + resetCode);

        // Création de l'objet VerificationCode
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(resetCode);
        verificationCode.setEmail(identifier);
        verificationCode.setExpiryDate(LocalDateTime.now().plusMinutes(15)); // Expiration dans 15 minutes

        // Sauvegarde dans la base de données
        verificationCodeRepository.save(verificationCode);
        System.out.println("Code sauvegardé pour l'email : " + identifier);

        // Envoi par email
        emailService.sendSimpleMessage(
            identifier,
            "Password Reset Code",
            "Here is your password reset code: " + resetCode
        );

        return ResponseEntity.ok("Password reset code sent.");
    }

    return ResponseEntity.badRequest().body("Invalid identifier format.");
}
@Autowired
    private JwtTokenProvider jwtTokenProvider;
    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyResetCode(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        
        // Retrieve the verification code from the database
        Optional<VerificationCode> optionalVerificationCode = verificationCodeRepository.findByCode(code);
        
        // If the code is missing or expired, throw an exception
        VerificationCode verificationCode = optionalVerificationCode.orElseThrow(() -> 
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code invalide ou expiré")
        );
    
        // Check if the code has expired
        if (verificationCode.isExpired()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Code expiré.");
        }
    
        // If the code is valid, generate a JWT token for the user
        String token = jwtTokenProvider.createToken(verificationCode.getEmail());
    
        // Return the token in the response
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
    
        return ResponseEntity.ok(response);
    }
    

  @Autowired
    private UserRepository userRepository;

    
 
    @PostMapping("/reset-password")
public ResponseEntity<?> resetPassword(
        @RequestParam("newPassword") String newPassword,
        @RequestParam("confirmPassword") String confirmPassword) {

    // Vérifiez si les mots de passe correspondent
    if (!newPassword.equals(confirmPassword)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Les mots de passe ne correspondent pas.");
    }

    // Récupérer l'utilisateur connecté via le contexte de sécurité
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // Vérifiez si l'utilisateur est authentifié
    if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié.");
    }

    // Récupérez l'email de l'utilisateur connecté
    String email = ((UserDetails) authentication.getPrincipal()).getUsername();

    // Recherchez l'utilisateur dans la base de données
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
    }

    // Mettre à jour le mot de passe
    User user = optionalUser.get();
    user.setPassword(passwordEncoder.encode(newPassword)); // Encoder le mot de passe avant de le sauvegarder
    userRepository.save(user);

    return ResponseEntity.ok("Le mot de passe a été mis à jour avec succès.");
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

            Client updatedClient = clientService.updateClientProfile(client.getId(), updateDto, photo);

        
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
    
    @PostMapping("/chang-password")
public ResponseEntity<?> changePassword(
        @RequestHeader("Authorization") String token,
        @RequestBody ChangePassword changePassword) {

    // Enlever "Bearer " pour obtenir uniquement le token JWT
    String extractedToken = token.substring(7);

    // Obtenir l'utilisateur à partir du jeton
    User user = authenticationService.getUserFromToken(extractedToken);

    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non trouvé");
    }

    // Vérifier si les nouveaux mots de passe correspondent
    if (!changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Les mots de passe ne correspondent pas");
    }

    // Mettre à jour le mot de passe de l'utilisateur
    authenticationService.updatePassword(user, changePassword.getNewPassword());

    return ResponseEntity.ok("Mot de passe changé avec succès");
}

    
    
}
