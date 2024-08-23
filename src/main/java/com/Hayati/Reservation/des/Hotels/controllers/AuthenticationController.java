package com.Hayati.Reservation.des.Hotels.controllers;

import com.Hayati.Reservation.des.Hotels.dto.LoginUserDto;
import com.Hayati.Reservation.des.Hotels.dto.RegisterUserDto;
import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.responses.LoginResponse;
import com.Hayati.Reservation.des.Hotels.services.AuthenticationService;
import com.Hayati.Reservation.des.Hotels.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RequestMapping("/api/auth")
@RestController
@CrossOrigin(origins = "*")  // Temporarily allowing all origins for development
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            // Attempt authentication
            String jwtToken = authenticationService.authenticate(loginUserDto);

            // Prepare response object with JWT
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());
            loginResponse.setUserType("User");

            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            System.err.println("Authentication error: " + e.getMessage());
            return ResponseEntity.status(401).body("Invalid credentials or user not found.");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(
            @RequestPart("user") String registerUserDtoJson,
            @RequestPart("photo") MultipartFile photo) {
        try {
            // Parse JSON for registration
            ObjectMapper objectMapper = new ObjectMapper();
            RegisterUserDto registerUserDto = objectMapper.readValue(registerUserDtoJson, RegisterUserDto.class);

            // Handle file upload
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            String uploadDir = "src/main/resources/static/images/";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            Files.copy(photo.getInputStream(), Paths.get(uploadDir + fileName), StandardCopyOption.REPLACE_EXISTING);

            // Save user with uploaded image
            String imageUrl = "/images/" + fileName;
            User registeredUser = authenticationService.signUp(registerUserDto, imageUrl);

            return ResponseEntity.ok(registeredUser);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error during registration: " + e.getMessage());
        }
    }
}
