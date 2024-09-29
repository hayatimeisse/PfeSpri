package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.LoginUserDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterUserDto;
import com.Hayati.Reservation.des.Hotels.entity.Role;
import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.repositoriy.RoleRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;  // Import correct pour IOException
import java.nio.file.Files;   // Import correct pour Files
import java.nio.file.Path;
import java.nio.file.Paths;   // Import correct pour Paths
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService {

    private final String USER_IMAGE_UPLOAD_DIR = "C:/Pfe/Reservation_users/";
    private final String BASE_USER_IMAGE_URL = "http://192.168.100.61:9001/";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public AuthenticationService(UserRepository userRepository,
                                 AuthenticationManager authenticationManager,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    // Méthode pour sauvegarder l'image de l'utilisateur
    private String saveUserImage(MultipartFile photo) {
        try {
            // Générer un nom de fichier unique
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            // Créer le chemin vers le fichier
            Path path = Paths.get(USER_IMAGE_UPLOAD_DIR + fileName);
            // Créer les répertoires si nécessaire
            Files.createDirectories(path.getParent());
            // Sauvegarder l'image
            Files.write(path, photo.getBytes());
            return fileName;  // Retourne le nom du fichier
        } catch (IOException e) {
            throw new RuntimeException("Échec du stockage de l'image", e);
        }
    }

    public User signUp(RegisterUserDto registerUserDto, MultipartFile photo) {
        // Vérification que l'email N'EXISTE PAS dans la base de données
        Optional<User> existingEmailUser = userRepository.findByEmail(registerUserDto.getEmail());
        if (existingEmailUser.isPresent()) {  // Si l'email existe, on lève une exception
            throw new RuntimeException("L'email que vous avez entré existe déjà dans le système. Veuillez utiliser un autre email.");
        }

        // Validation du format de l'email (facultatif, car cela peut être géré par @Email)
        if (!isValidEmailFormat(registerUserDto.getEmail())) {
            throw new RuntimeException("Le format de l'email est incorrect.");
        }

        // Validation du numéro de téléphone Mauritanien
        String phoneNumber = registerUserDto.getNumerodetelephone();
        if (!phoneNumber.matches("^[234]\\d{7}$")) {  // Numéro doit commencer par 2, 3 ou 4 et avoir 8 chiffres
            throw new RuntimeException("Le numéro de téléphone doit être un numéro valide en Mauritanie.");
        }

        // Sauvegarde de l'image de l'utilisateur
        String imageUrl = saveUserImage(photo);  // Sauvegarde l'image et retourne le nom du fichier

        // Création du nouvel utilisateur
        User user = new User();
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword())); // Hashage du mot de passe
        user.setName(registerUserDto.getName());
        user.setNumerodetelephone(registerUserDto.getNumerodetelephone());
        user.setImageUrl(imageUrl);  // Stocker le chemin de l'image

        // Assigner un rôle par défaut
        Role userRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));
        user.setRoles(Set.of(userRole));

        // Sauvegarder l'utilisateur
        return userRepository.save(user);
    }

    // Méthode pour vérifier le format de l'email (optionnelle si vous utilisez @Email)
    private boolean isValidEmailFormat(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    public User findByEmailOrPhone(String emailOrPhone) {
        return userRepository.findByEmailOrNumerodetelephone(emailOrPhone, emailOrPhone)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public String authenticate(LoginUserDto input) {
        User user = userRepository.findByEmailOrNumerodetelephone(input.getEmailOrnumerodetelephone(), input.getEmailOrnumerodetelephone())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            return jwtService.generateTokenForUser(user);
        } else {
            throw new RuntimeException("Mot de passe incorrect");
        }
    }

    public User findUserByToken(String token) {
        String emailOrPhone = jwtService.extractUsername(token);
        return userRepository.findByEmailOrNumerodetelephone(emailOrPhone, emailOrPhone)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));  // Hash du nouveau mot de passe
        userRepository.save(user);  // Enregistrer l'utilisateur mis à jour
        System.out.println("Mot de passe mis à jour avec succès pour l'utilisateur : " + user.getEmail());
    }
}

