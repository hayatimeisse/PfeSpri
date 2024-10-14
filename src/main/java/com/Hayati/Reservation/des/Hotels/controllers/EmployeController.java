// package backendpfe.example.backendEDUFormation.Controllers;

// import backendpfe.example.backendEDUFormation.DTO.RegisterEmployeeDto;
// import backendpfe.example.backendEDUFormation.DTO.UpdateEmployeeDto;
// import backendpfe.example.backendEDUFormation.Entity.Employe;
// import backendpfe.example.backendEDUFormation.Services.EmployeService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;

// import java.util.List;
// import java.util.Optional;

// @CrossOrigin(origins = "*", allowedHeaders = "*")
// @RequestMapping("/employes")
// @RestController
// public class EmployeController {

//     private final EmployeService employeService;

//     @Autowired
//     public EmployeController(EmployeService employeService) {
//         this.employeService = employeService;
//     }

//     // List all employees
//     @GetMapping("/list")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<List<Employe>> listEmployes() {
//         List<Employe> employes = employeService.getAllEmployes();

//         // Append full image URL to each employee
//         employes.forEach(employe -> {
//             if (employe.getPhoto() != null && !employe.getPhoto().isEmpty()) {
//                 String imageUrl = "http://localhost:8080/" + employe.getPhoto();
//                 employe.setPhoto(imageUrl);
//             }
//             if (employe.getDiplome() != null && !employe.getDiplome().isEmpty()) {
//                 String diplomeUrl = "http://localhost:8080/" + employe.getDiplome();
//                 employe.setDiplome(diplomeUrl);
//             }
//         });
//         return ResponseEntity.ok(employes);
//     }

//     // Get a specific employee by ID
//     @GetMapping("/Employe/{id}")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<Employe> getEmployeById(@PathVariable Long id) {
//         Optional<Employe> employe = employeService.getEmployeById(id);
//         if (employe.isPresent()) {
//             Employe e = employe.get();
//             if (e.getPhoto() != null && !e.getPhoto().isEmpty()) {
//                 String imageUrl = "http://localhost:8080/" + e.getPhoto();
//                 e.setPhoto(imageUrl);
//             }
//             if (e.getDiplome() != null && !e.getDiplome().isEmpty()) {
//                 String diplomeUrl = "http://localhost:8080/" + e.getDiplome();
//                 e.setDiplome(diplomeUrl);
//             }
//             return ResponseEntity.ok(e);
//         } else {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//         }
//     }

//     // Delete an employee
//     @DeleteMapping("/delete/{id}")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<String> deleteEmploye(@PathVariable Long id) {
//         employeService.deleteEmploye(id);
//         return ResponseEntity.ok("Employe with ID " + id + " has been deleted successfully.");
//     }

//     // Create a new employee
//     @PostMapping("/create/employe")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<?> createEmploye(
//             @RequestParam("email") String email,
//             @RequestParam("password") String password,
//             @RequestParam("nom") String nom,
//             @RequestParam("photo") MultipartFile photo,
//             @RequestParam("diplome") MultipartFile diplome,
//             @RequestParam("departement") String departement) {

//         // Log the received data
//         System.out.println("Creating new employee with the following details:");
//         System.out.println("Email: " + email);
//         System.out.println("Name: " + nom);
//         System.out.println("Department: " + departement);
//         System.out.println("Photo file: " + photo.getOriginalFilename());
//         System.out.println("Diplome file: " + diplome.getOriginalFilename());

//         RegisterEmployeeDto registerEmployeeDto = new RegisterEmployeeDto()
//                 .setEmail(email)
//                 .setPassword(password)
//                 .setNom(nom)
//                 .setPhoto(photo)
//                 .setDiplome(diplome)
//                 .setDepartement(departement);

//         Employe createdEmploye = employeService.createEmploye(registerEmployeeDto);
//         if (createdEmploye != null && createdEmploye.getPhoto() != null) {
//             String imageUrl = "http://localhost:8080/" + createdEmploye.getPhoto();
//             createdEmploye.setPhoto(imageUrl);
//         }
//         if (createdEmploye != null && createdEmploye.getDiplome() != null) {
//             String diplomeUrl = "http://localhost:8080/" + createdEmploye.getDiplome();
//             createdEmploye.setDiplome(diplomeUrl);
//         }
//         return ResponseEntity.ok(createdEmploye);
//     }

//     ///////////////
//     ///statistic
//     @GetMapping("/count")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<Long> getCountOfEmployees() {
//         long count = employeService.getEmployeeCount();
//         return ResponseEntity.ok(count);
//     }

//     // Update an existing employee
//     // Update an existing employee
//     @PutMapping("/update/{id}")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<?> updateEmploye(
//             @PathVariable Long id,
//             @RequestParam("email") String email,
//             @RequestParam("password") String password,
//             @RequestParam("nom") String nom,
//             @RequestParam(value = "photo", required = false) MultipartFile photo,
//             @RequestParam(value = "diplome", required = false) MultipartFile diplome,
//             @RequestParam("departement") String departement,
//             @RequestParam("active") boolean active) {

//         UpdateEmployeeDto updateEmployeeDto = new UpdateEmployeeDto()
//                 .setEmail(email)
//                 .setPassword(password)
//                 .setNom(nom)
//                 .setPhoto(photo)
//                 .setDiplome(diplome)
//                 .setDepartement(departement)
//                 .setActive(active);

//         try {
//             Employe updatedEmploye = employeService.updateEmploye(updateEmployeeDto, id);

//             if (updatedEmploye.getPhoto() != null && !updatedEmploye.getPhoto().startsWith("http") && !updatedEmploye.getPhoto().isEmpty()) {
//                 String imageUrl = "http://localhost:8080/" + updatedEmploye.getPhoto();
//                 updatedEmploye.setPhoto(imageUrl);
//             }
//             if (updatedEmploye.getDiplome() != null && !updatedEmploye.getDiplome().startsWith("http") && !updatedEmploye.getDiplome().isEmpty()) {
//                 String diplomeUrl = "http://localhost:8080/" + updatedEmploye.getDiplome();
//                 updatedEmploye.setDiplome(diplomeUrl);
//             }

//             return ResponseEntity.ok(updatedEmploye);
//         } catch (RuntimeException e) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found or update failed.");
//         }
//     }
// }