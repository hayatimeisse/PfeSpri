// package com.Hayati.Reservation.des.Hotels.controllers;

// import org.springframework.core.io.Resource;
// import org.springframework.core.io.UrlResource;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import java.io.IOException;
// import java.net.MalformedURLException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;

// @RestController
// @RequestMapping("/subscribe_photos")
// public class ImageController {

//     private final Path photoDir = Paths.get("C:/Pfe/Reservation_Subscribe/subscribe_photos");

//     @GetMapping("/{filename:.+}")
//     public ResponseEntity<Resource> getPhoto(@PathVariable String filename) {
//         try {
//             // Resolve the file path
//             Path file = photoDir.resolve(filename).normalize();

//             // Load the file as a resource
//             Resource resource = new UrlResource(file.toUri());

//             if (resource.exists() && resource.isReadable()) {
//                 // Determine the file's content type
//                 String contentType = Files.probeContentType(file);
//                 if (contentType == null) {
//                     contentType = "application/octet-stream"; // Default content type
//                 }

//                 return ResponseEntity.ok()
//                         .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//                         .contentType(MediaType.parseMediaType(contentType))
//                         .body(resource);
//             } else {
//                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//             }
//         } catch (MalformedURLException e) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//         } catch (IOException e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//         }
//     }
// }
