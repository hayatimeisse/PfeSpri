package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.LoginUserDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterAdminDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterClientDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterSubscribeDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterUserDto;
import com.Hayati.Reservation.des.Hotels.entity.Admin;
import com.Hayati.Reservation.des.Hotels.entity.Client;
import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.repositoriy.ClientRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.SubscribeRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.UserRepository;
import com.Hayati.Reservation.des.Hotels.responses.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;  // Import correct pour IOException
import java.nio.file.Files;   // Import correct pour Files
import java.nio.file.Path;
import java.nio.file.Paths;   // Import correct pour Paths
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    ///////



    ////////////////////////signupEmploye
    @Autowired
    private ClientRepository clientRepository;
    private final String IMAGE_UPLOAD_DIR = "C:/Pfe/Reservation_client/";


    public Client signupClient(RegisterClientDto input) {
        // Créer un nouvel objet client
        Client client = new Client();
        
        // Définir les détails de l'utilisateur
        client.setName(input.getNom());
        client.setEmail(input.getEmail());
        client.setPassword(passwordEncoder.encode(input.getPassword()));
        client.setNumerodetelephone(input.getNumerodetelephone());
    
        // Enregistrer l'utilisateur dans la table 'users'
        User savedUser = userRepository.save(client); // L'utilisateur est enregistré dans 'users'
        
        // Définir l'ID utilisateur du client après l'enregistrement de l'utilisateur
        client.setId(savedUser.getId());
    
        // Enregistrer la photo si elle est présente
        if (input.getPhoto() != null && !input.getPhoto().isEmpty()) {
            String photoPath = saveImage(input.getPhoto(), "client_photos/");
            client.setPhoto(photoPath); // Assigner le chemin de la photo ici
        } else {
            throw new RuntimeException("Une photo est requise pour l'inscription");
        }
        
        // Ajouter le rôle 'ROLE_CLIENT'
        client.getRoles().add("ROLE_CLIENT");
    
        // Enregistrer l'entité Client avec la photo associée
        return clientRepository.save(client);
    }
    
    
    // Directory path to save employee's files (photos and diplomas)
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
    


    /////////////////////////////signupFormateur

    @Autowired
    private SubscribeRepository subscribeRepository;
    private final String BASE_IMAGE_UPLOAD_DIR = "C:/Pfe/Reservation_Subscribe/";


    public Subscribe signupSubscribe(RegisterSubscribeDto input) {
        // Créer un nouvel objet client
        Subscribe subscribe = new Subscribe();
        
        // Définir les détails de l'utilisateur
        subscribe.setName(input.getNom());
        subscribe.setEmail(input.getEmail());
        subscribe.setPassword(passwordEncoder.encode(input.getPassword()));
    
        // Enregistrer l'utilisateur dans la table 'users'
        User savedUser = userRepository.save(subscribe); // L'utilisateur est enregistré dans 'users'
        
        // Définir l'ID utilisateur du client après l'enregistrement de l'utilisateur
        subscribe.setId(savedUser.getId());
    
        // Enregistrer la photo si elle est présente
        if (input.getPhoto() != null && !input.getPhoto().isEmpty()) {
            String photoPath = saveImage(input.getPhoto(), "subscribe_photos/");
            subscribe.setPhoto(photoPath); // Assigner le chemin de la photo ici
        } else {
            throw new RuntimeException("Une photo est requise pour l'inscription");
        }
        
        // Ajouter le rôle 'ROLE_CLIENT'
        subscribe.getRoles().add("ROLE_SUBSCRIBE");
    
        // Enregistrer l'entité Client avec la photo associée
        return subscribeRepository.save(subscribe);
    }
    

    /////
    public User signupAdmin(RegisterAdminDto input) {
        var admin = new Admin();
                admin.setName(input.getNom());
               admin.setEmail(input.getEmail());
                admin.setPassword(passwordEncoder.encode(input.getPassword()));
        admin.getRoles().add("ROLE_ADMIN");

        return userRepository.save(admin);
    }





    public LoginResponse authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmailOrnumerodetelephone(),
                        input.getPassword()
                )
        );

        User user = userRepository.findByEmail(input.getEmailOrnumerodetelephone()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);
        response.setExpiresIn(jwtService.getExpirationTime());

        if (user instanceof Client) {
            response.setUserType("Client");
            response.setUserData(user);
        // } else if (user instanceof Formateur) {
        //     response.setUserType("Formateur");
        //     response.setUserData(user);
        } else if (user instanceof Admin) {
            response.setUserType("Admin");
            response.setUserData(user);
        } else {
            response.setUserType("User");
            response.setUserData(user);
        }

        return response;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}

