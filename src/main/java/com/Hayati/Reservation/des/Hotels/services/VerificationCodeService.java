package com.Hayati.Reservation.des.Hotels.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.entity.VerificationCode;
import com.Hayati.Reservation.des.Hotels.repositoriy.UserRepository;
import com.Hayati.Reservation.des.Hotels.repositoriy.VerificationCodeRepository;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public VerificationCodeService(VerificationCodeRepository verificationCodeRepository,
                                   UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        this.verificationCodeRepository = verificationCodeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void deleteByEmail(String email) {
        verificationCodeRepository.deleteByEmail(email);
        System.out.println("Code supprimé pour l'email : " + email);
    }
    @Transactional
public void deleteVerificationCode(String code) {
    verificationCodeRepository.deleteByCode(code);
}

   
    public Optional<VerificationCode> findByCode(String code) {
        System.out.println("Recherche du code : " + code);
        Optional<VerificationCode> result = verificationCodeRepository.findByCode(code);
        System.out.println("Résultat trouvé : " + (result.isPresent() ? "Oui" : "Non"));
        return result;
    }

    public VerificationCode save(VerificationCode verificationCode) {
        return verificationCodeRepository.save(verificationCode);
    }

    // public boolean validateVerificationCode(String code) {
    //     Optional<VerificationCode> optionalCode = verificationCodeRepository.findByCode(code);

    //     if (optionalCode.isPresent()) {
    //         VerificationCode verificationCode = optionalCode.get();

    //         // Check if the code is expired
    //         if (verificationCode.getExpiryDate().isBefore(LocalDateTime.now())) {
    //             return false; // Code expired
    //         }
    //         return true; // Code is valid
    //     }
    //     return false; // Code not found
    // }
    public boolean validateVerificationCode(String code) {
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findByCode(code);
    
        if (verificationCode.isPresent()) {
            VerificationCode storedCode = verificationCode.get();
    
            // Vérifiez si le code a expiré
            if (storedCode.getExpiryDate().isBefore(LocalDateTime.now())) {
                System.out.println("Code expiré pour l'email : " + storedCode.getEmail());
                return false; // Le code est expiré
            }
    
            return true; // Le code est valide
        }
    
        System.out.println("Code introuvable : " + code);
        return false; // Le code n'existe pas
    }
    
    public boolean resetPassword(String code, String newPassword) {
        try {
            // Rechercher le code de vérification
            VerificationCode verificationCode = verificationCodeRepository.findByCode(code)
                    .orElseThrow(() -> new IllegalArgumentException("Code invalide."));
    
            // Rechercher l'utilisateur associé à l'email du code
            User user = userRepository.findByEmail(verificationCode.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable."));
    
            // Réinitialiser le mot de passe
            user.setPassword(passwordEncoder.encode(newPassword)); // Encodez le mot de passe
            userRepository.save(user);
    
            // Supprimer le code de vérification
            verificationCodeRepository.delete(verificationCode);
    
            System.out.println("Mot de passe réinitialisé avec succès pour : " + user.getEmail());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
