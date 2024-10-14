package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.LoginUserDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterAdminDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterClientDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterSubscribeDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterUserDto;
import com.Hayati.Reservation.des.Hotels.dto.UserProfileUpdateRequest;
import com.Hayati.Reservation.des.Hotels.entity.Client;
import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.responses.ChangePasswordRequest;
import com.Hayati.Reservation.des.Hotels.responses.LoginResponse;
import com.Hayati.Reservation.des.Hotels.services.AuthenticationService;
import com.Hayati.Reservation.des.Hotels.services.JwtService;
import com.Hayati.Reservation.des.Hotels.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RequestMapping("/api/auth")
@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

   
 

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }



    @PostMapping("signup/client")
    public ResponseEntity<?> signupClient(
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam("nom") String nom,
        @RequestParam("photo") MultipartFile photo,  // Make sure this is passed as a file
        @RequestParam("numerodetelephone") String numerodetelephone) {
    
        // Log incoming parameters for debugging
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Name: " + nom);
        System.out.println("Phone: " + numerodetelephone);
        System.out.println("Photo: " + (photo != null ? photo.getOriginalFilename() : "No Photo"));
    
        if (photo == null || photo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Photo is required");
        }
    
        // Continue with registration
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


    @PostMapping("signup/subscribe")
    public ResponseEntity<?> signupSubscribe(
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam("nom") String nom,
        @RequestParam("photo") MultipartFile photo ) {
    
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Name: " + nom);
        System.out.println("Photo: " + (photo != null ? photo.getOriginalFilename() : "No Photo"));
    
        if (photo == null || photo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Photo is required");
        }
    
        RegisterSubscribeDto registerSubscribeDto = new RegisterSubscribeDto()
                .setEmail(email)
                .setPassword(password)
                .setNom(nom)
                .setPhoto(photo);
    
                Subscribe createdSubscribe = authenticationService.signupSubscribe(registerSubscribeDto);
        if (createdSubscribe != null && createdSubscribe.getPhoto() != null) {
            String imageUrl = "http://localhost:9001/" + createdSubscribe.getPhoto();
            createdSubscribe.setPhoto(imageUrl);
        }
    
        return ResponseEntity.ok(createdSubscribe);
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
    


}
