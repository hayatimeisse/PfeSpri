package com.Hayati.Reservation.des.Hotels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

import com.Hayati.Reservation.des.Hotels.dto.RegisterClientDto;
import com.Hayati.Reservation.des.Hotels.dto.UpdateClientDto;
import com.Hayati.Reservation.des.Hotels.entity.Client;
import com.Hayati.Reservation.des.Hotels.services.ClientService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth/client")
@RestController
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // List all clients
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Client>> listClients() {
        List<Client> clients = clientService.getAllClients();
    
       
        return ResponseEntity.ok(clients);
    }
    

    // Get a specific client by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            Client c = client.get();
           
            return ResponseEntity.ok(c);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a client
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok("Client with ID " + id + " has been deleted successfully.");
    }

    // Create a new client
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createClient(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("nom") String nom,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("numerodetelephone") String numerodetelephone) {

        RegisterClientDto registerClientDto = new RegisterClientDto()
                .setEmail(email)
                .setPassword(password)
                .setNom(nom)
                .setPhoto(photo)
                .setNumerodetelephone(numerodetelephone);

        Client createdClient = clientService.createClient(registerClientDto);
      
        return ResponseEntity.ok(createdClient);
    }

    // Get the count of clients
    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getCountOfClients() {
        long count = clientService.getClientCount();
        return ResponseEntity.ok(count);
    }

    // Update an existing client
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateClient(
            @PathVariable Long id,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("nom") String nom,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam("numerodetelephone") String numerodetelephone) {

        UpdateClientDto updateClientDto = new UpdateClientDto()
                .setEmail(email)
                .setPassword(password)
                .setNom(nom)
                .setPhoto(photo)
                .setNumerodetelephone(numerodetelephone);

        try {
            Client updatedClient = clientService.updateClient(updateClientDto, id);

            return ResponseEntity.ok(updatedClient);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found or update failed.");
        }
    }
}
