package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.entity.Client;
import com.Hayati.Reservation.des.Hotels.repositoriy.ClientRepository;
import com.Hayati.Reservation.des.Hotels.dto.RegisterClientDto;
import com.Hayati.Reservation.des.Hotels.dto.UpdateClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Directory path to save client's files (photos)
    private final String IMAGE_UPLOAD_DIR = "C:/Pfe/Reservation_client/";
    private final String BASE_IMAGE_URL = "http://192.168.100.191:9001/";

    
    // Méthode pour sauvegarder les fichiers clients
    private String saveImage(MultipartFile photo, String subDir) {
        try {
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            Path path = Paths.get(IMAGE_UPLOAD_DIR + subDir + fileName);
            Files.createDirectories(path.getParent()); // Crée le répertoire si nécessaire
            Files.write(path, photo.getBytes());
            return subDir + fileName;  // Retourner le chemin relatif
        } catch (IOException e) {
            throw new RuntimeException("Échec de l'enregistrement de l'image", e);
        }
    }

    // Méthode de création d'un client avec téléchargement de photo
    public Client createClient(RegisterClientDto input) {
        // Créer un nouvel objet client
        Client client = new Client();
        client.setName(input.getNom());
        client.setEmail(input.getEmail());
        client.setPassword(passwordEncoder.encode(input.getPassword()));
    
        // Ajouter le rôle 'ROLE_CLIENT'
        client.getRoles().add("ROLE_CLIENT");
    
        // Enregistrer le client dans la base de données
        Client savedClient = clientRepository.save(client);
    
      
        return savedClient;
    }
    
    

    // Update an existing client
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Client updateClient(UpdateClientDto input, Long id) {
        return clientRepository.findById(id).map(existingClient -> {
           

            existingClient.setName(input.getNom());
            existingClient.setEmail(input.getEmail());
            if (input.getPassword() != null && !input.getPassword().isEmpty()) {
                existingClient.setPassword(passwordEncoder.encode(input.getPassword()));
            }

            Client updatedClient = clientRepository.save(existingClient);

           

            return updatedClient;
        }).orElseThrow(() -> new RuntimeException("Client not found"));
    }

    // Get all clients
    @PreAuthorize("hasRole('ADMIN')")
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Get client by ID
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    // Delete a client
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteClient(Long id) {
        clientRepository.findById(id).ifPresentOrElse(clientRepository::delete, 
            () -> {
                throw new RuntimeException("Client not found with id: " + id);
            });
    }
    @Transactional
    public Client updateClientProfile(Long clientId, UpdateClientDto updateDto, MultipartFile photo) {
        // Récupérer le client
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Mettre à jour l'email
        if (updateDto.getEmail() != null && !updateDto.getEmail().isEmpty()) {
            client.setEmail(updateDto.getEmail());
        }

        // Mettre à jour le mot de passe (si fourni)
        if (updateDto.getPassword() != null && !updateDto.getPassword().isEmpty()) {
            client.setPassword(passwordEncoder.encode(updateDto.getPassword()));
        }

     
        Client updatedClient = clientRepository.save(client);

       

        return updatedClient;
    }

    // Get the count of clients
    @PreAuthorize("hasRole('ADMIN')")
    public long getClientCount() {
        return clientRepository.count();
    }
}
