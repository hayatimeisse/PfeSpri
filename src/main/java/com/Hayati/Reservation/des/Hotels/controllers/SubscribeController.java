// package com.Hayati.Reservation.des.Hotels.controllers;


// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;
// import java.util.List;
// import java.util.Optional;

// import com.Hayati.Reservation.des.Hotels.dto.RegisterClientDto;
// import com.Hayati.Reservation.des.Hotels.dto.UpdateClientDto;
// import com.Hayati.Reservation.des.Hotels.entity.Client;
// import com.Hayati.Reservation.des.Hotels.services.ClientService;
// import com.Hayati.Reservation.des.Hotels.services.SubscribeService;

// @CrossOrigin(origins = "*", allowedHeaders = "*")
// @RequestMapping("/api/auth/subscribe")
// @RestController
// public class SubscribeController {
//     private final SubscribeService subscribeService;

//     @Autowired
//     public SubscribeController(SubscribeService subscribeService) {
//         this.subscribeService = subscribeService;
//     }

//     // List all clients
//     @GetMapping("/list")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<List<Client>> listClients() {
//         List<Client> clients = clientService.getAllClients();

//         // Append full image URL to each client
//         clients.forEach(client -> {
//             if (client.getPhoto() != null && !client.getPhoto().isEmpty()) {
//                 String imageUrl = "http://localhost:9001/" + client.getPhoto();
//                 client.setPhoto(imageUrl);
//             }
//         });
//         return ResponseEntity.ok(clients);
//     }

//     // Get a specific client by ID
//     @GetMapping("/{id}")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<Client> getClientById(@PathVariable Long id) {
//         Optional<Client> client = clientService.getClientById(id);
//         if (client.isPresent()) {
//             Client c = client.get();
//             if (c.getPhoto() != null && !c.getPhoto().isEmpty()) {
//                 String imageUrl = "http://localhost:9001" + c.getPhoto();
//                 c.setPhoto(imageUrl);
//             }
//             return ResponseEntity.ok(c);
//         } else {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//         }
//     }

//     // Delete a client
//     @DeleteMapping("/delete/{id}")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<String> deleteClient(@PathVariable Long id) {
//         clientService.deleteClient(id);
//         return ResponseEntity.ok("Client with ID " + id + " has been deleted successfully.");
//     }

//     // Create a new client
//     @PostMapping("/create")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<?> createClient(
//             @RequestParam("email") String email,
//             @RequestParam("password") String password,
//             @RequestParam("nom") String nom,
//             @RequestParam("photo") MultipartFile photo,
//             @RequestParam("numerodetelephone") String numerodetelephone) {

//         RegisterClientDto registerClientDto = new RegisterClientDto()
//                 .setEmail(email)
//                 .setPassword(password)
//                 .setNom(nom)
//                 .setPhoto(photo)
//                 .setNumerodetelephone(numerodetelephone);

//         Client createdClient = clientService.createClient(registerClientDto);
//         if (createdClient != null && createdClient.getPhoto() != null) {
//             String imageUrl = "http://localhost:9001/" + createdClient.getPhoto();
//             createdClient.setPhoto(imageUrl);
//         }
//         return ResponseEntity.ok(createdClient);
//     }

//     // Get the count of clients
//     @GetMapping("/count")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<Long> getCountOfClients() {
//         long count = clientService.getClientCount();
//         return ResponseEntity.ok(count);
//     }

//     // Update an existing client
//     @PutMapping("/update/{id}")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<?> updateClient(
//             @PathVariable Long id,
//             @RequestParam("email") String email,
//             @RequestParam("password") String password,
//             @RequestParam("nom") String nom,
//             @RequestParam(value = "photo", required = false) MultipartFile photo,
//             @RequestParam("numerodetelephone") String numerodetelephone) {

//         UpdateClientDto updateClientDto = new UpdateClientDto()
//                 .setEmail(email)
//                 .setPassword(password)
//                 .setNom(nom)
//                 .setPhoto(photo)
//                 .setNumerodetelephone(numerodetelephone);

//         try {
//             Client updatedClient = clientService.updateClient(updateClientDto, id);

//             if (updatedClient.getPhoto() != null && !updatedClient.getPhoto().startsWith("http") && !updatedClient.getPhoto().isEmpty()) {
//                 String imageUrl = "http://localhost:9001/" + updatedClient.getPhoto();
//                 updatedClient.setPhoto(imageUrl);
//             }
//             return ResponseEntity.ok(updatedClient);
//         } catch (RuntimeException e) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found or update failed.");
//         }
//     }
// }
