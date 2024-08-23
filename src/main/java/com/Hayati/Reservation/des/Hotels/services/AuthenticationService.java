package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.LoginUserDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterUserDto;
import com.Hayati.Reservation.des.Hotels.entity.Role;
import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.repositoriy.RoleRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AuthenticationService {
  
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtService jwtService, // Ajouter JwtService
            RoleRepository roleRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

 
    public String authenticate(LoginUserDto input) {
        User user = userRepository.findByEmail(input.getEmailOrnumerodetelephone())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            return jwtService.generateTokenForUser(user);
        } else {
            throw new RuntimeException("Invalid password");
        }
    }

    // Méthode pour enregistrer un nouvel utilisateur
    public User signUp(RegisterUserDto registerUserDto, String imageUrl) {
        User user = new User();
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setName(registerUserDto.getName());
        user.setNumerodetelephone(registerUserDto.getNumerodetelephone());
        user.setImageUrl(imageUrl); // Sauvegarder l'image de l'utilisateur

        // Assigner le rôle 'ROLE_ADMIN' à l'utilisateur (ou tout autre rôle)
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));
        user.setRoles(Set.of(adminRole));

        return userRepository.save(user);
    }

 
    // Récupérer la liste des utilisateurs
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
