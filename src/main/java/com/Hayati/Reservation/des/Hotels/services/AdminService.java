package com.Hayati.Reservation.des.Hotels.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Hayati.Reservation.des.Hotels.dto.UpdateAdminDto;
import com.Hayati.Reservation.des.Hotels.entity.Admin;
import com.Hayati.Reservation.des.Hotels.repositoriy.UserRepository;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin updateAdmin(Long adminId, UpdateAdminDto input) {
        // Retrieve existing Admin entity from the database
        Admin admin = (Admin) userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + adminId));

        // Update the Admin entity with new data
        if (input.getNom() != null) {
            admin.setName(input.getNom());
        }
        if (input.getEmail() != null) {
            admin.setEmail(input.getEmail());
        }
        if (input.getPassword() != null) {
            admin.setPassword(passwordEncoder.encode(input.getPassword()));
        }
       

        // Save the updated Admin entity back to the repository
        return userRepository.save(admin);
    }

    public void deleteAdmin(Long adminId) {
        // Vérifier si l'admin existe avant de le supprimer
        Admin admin = (Admin) userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + adminId));
        // Supprimer l'admin de la base de données
        userRepository.delete(admin);
    }
}
