// package com.Hayati.Reservation.des.Hotels.controllers;

// import com.Hayati.Reservation.des.Hotels.dto.ChambreDto;
// import com.Hayati.Reservation.des.Hotels.dto.EmployeeDto;
// import com.Hayati.Reservation.des.Hotels.services.ChambreService;
// import com.Hayati.Reservation.des.Hotels.services.EmployeeService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/auth/Employee")
// public class EmployeeController {

//     @Autowired
//     private EmployeeService employeeService;

//     @PostMapping
//     public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
//         EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
//         return ResponseEntity.ok(createdEmployee);
//     }

//     @GetMapping
//     public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
//         List<EmployeeDto> employees = employeeService.getAllEmployees();
//         return ResponseEntity.ok(employees);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
//         EmployeeDto employee = employeeService.getEmployeeById(id);
//         return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
//         EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDto);
//         return updatedEmployee != null ? ResponseEntity.ok(updatedEmployee) : ResponseEntity.notFound().build();
//     }
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
//         employeeService.deleteEmployee(id);
//         return ResponseEntity.noContent().build();
//     }
    
// }
