package com.Hayati.Reservation.des.Hotels.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.Hayati.Reservation.des.Hotels.dto.LoginDTO;
import com.Hayati.Reservation.des.Hotels.dto.SignupDto;
import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.repositoriy.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto) {
        logger.info("Authenticating user with emailOrnumerodetelephone: {}", loginDto.getEmailOrnumerodetelephone());
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDto.getEmailOrnumerodetelephone(), loginDto.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("User signed-in successfully!");
            return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);
        } catch (BadCredentialsException e) {
            logger.error("Invalid email or password", e);
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("An error occurred during authentication", e);
            return new ResponseEntity<>("An error occurred during authentication", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signUpDto) {
        logger.info("Registering user with email: {}", signUpDto.getEmail());
        try {
            if (userRepository.existsByNumerodetelephone(signUpDto.getNumerodetelephone())) {
                logger.warn("Numero is already taken!");
                return new ResponseEntity<>("Numero is already taken!", HttpStatus.BAD_REQUEST);
            }

            if (userRepository.existsByEmail(signUpDto.getEmail())) {
                logger.warn("Email is already taken!");
                return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
            }

            User user = new User();
            user.setName(signUpDto.getName());
            user.setEmail(signUpDto.getEmail());
            user.setNumerodetelephone(signUpDto.getNumerodetelephone());
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

            userRepository.save(user);
            logger.info("User registered successfully");
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred during user registration", e);
            return new ResponseEntity<>("An error occurred during user registration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}