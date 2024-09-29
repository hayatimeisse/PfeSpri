package com.Hayati.Reservation.des.Hotels.services;

import org.springframework.stereotype.Service;

import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.repositoriy.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User findByEmailOrNumerodetelephone(String email, String numerodetelephone) {
        return userRepository.findByEmailOrNumerodetelephone(email, numerodetelephone).orElse(null);
    }
    

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User updateUserProfile(Long userId, String username, String emailOrPhone) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        if (username != null && !username.isEmpty()) {
            user.setName(username);
        }
    
        if (emailOrPhone != null && !emailOrPhone.isEmpty()) {
            if (emailOrPhone.contains("@")) {
                Optional<User> existingUser = userRepository.findByEmail(emailOrPhone);
                if (existingUser.isPresent() && existingUser.get().getId_user() != userId) {
                    throw new RuntimeException("Email already in use");
                }
                user.setEmail(emailOrPhone);
            } else {
                Optional<User> existingUser = userRepository.findByNumerodetelephone(emailOrPhone);
                if (existingUser.isPresent() && existingUser.get().getId_user() != userId) {
                    throw new RuntimeException("Phone number already in use");
                }
                user.setNumerodetelephone(emailOrPhone);
            }
        }
    
        return userRepository.save(user);
    }
    
    
    
}
