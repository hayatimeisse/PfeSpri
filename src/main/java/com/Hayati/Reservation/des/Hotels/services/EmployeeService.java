// package com.Hayati.Reservation.des.Hotels.services;

// import com.Hayati.Reservation.des.Hotels.dto.EmployeeDto;
// import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
// import com.Hayati.Reservation.des.Hotels.dto.ReservationDto;
// import com.Hayati.Reservation.des.Hotels.entity.Employee;
// import com.Hayati.Reservation.des.Hotels.entity.Hotel;
// import com.Hayati.Reservation.des.Hotels.entity.Paiement;
// import com.Hayati.Reservation.des.Hotels.entity.Reservation;
// import com.Hayati.Reservation.des.Hotels.repositoriy.EmployeeRepositoriy;
// import com.Hayati.Reservation.des.Hotels.repositoriy.HotelRepositoriy;

// import org.modelmapper.ModelMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// import java.util.stream.Collectors;

// @Service
// public class EmployeeService {

//     @Autowired
//     private EmployeeRepositoriy employeeRepositoriy;

//     @Autowired
//     private HotelRepositoriy hotelRepositoriy;

//     @Autowired
//     private ModelMapper modelMapper;

//      public EmployeeDto createEmployee(EmployeeDto employeeDto) {
//         Employee employee = modelMapper.map(employeeDto, Employee.class);
        
//         // Gestion du paiement
//         if (employeeDto.getHotel_id() != null) {
//             Optional<Hotel> hotel = hotelRepositoriy.findById(employeeDto.getHotel_id());
//             if (hotel.isPresent()) {
//                 employee.setHotel(hotel.get());
//             } else {
//                 // Si aucun paiement n'est trouvé avec l'id fourni, traiter selon les besoins (par exemple, renvoyer une erreur)
//                 // throw new RuntimeException("Paiement not found with id: " + reservationDto.getPaiement_id());
//             }
//         }
        
//         // Enregistrement de la réservation avec le paiement lié
//         employee = employeeRepositoriy.save(employee);
        
//         // Assurez-vous que le DTO retourné a l'id du paiement correctement configuré
//         EmployeeDto resultDto = modelMapper.map(employee, EmployeeDto.class);
//         resultDto.setHotel_id(employee.getHotel() != null ? employee.getHotel().getId_hot() : null);
        
//         return resultDto;
//     }

//     public List<EmployeeDto> getAllEmployees() {
//         return employeeRepositoriy.findAll()
//                 .stream()
//                 .map(employee -> modelMapper.map(employee, EmployeeDto.class))
//                 .collect(Collectors.toList());
//     }
    

//     public EmployeeDto getEmployeeById(Long id) {
//         return employeeRepositoriy.findById(id)
//                 .map(employee -> modelMapper.map(employee, EmployeeDto.class))
//                 .orElse(null);
//     }

  
//     public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
//         Optional<Employee> optionalEmployee = employeeRepositoriy.findById(id);
//         if (optionalEmployee.isPresent()) {
//             Employee employee = optionalEmployee.get();
//             employee.setName(employeeDto.getName());
//             employee.setNNI(employeeDto.getNNI());
           
//             // Fetch and set the hotel
//             Optional<Hotel> hotel = hotelRepositoriy.findById(employeeDto.getHotel_id());
//             hotel.ifPresent(employee::setHotel);
    
//             // Save the updated employee entity in the repository
//             employee = employeeRepositoriy.save(employee);
            
//             // Map the updated employee entity to an EmployeeDto object and return it
//             return modelMapper.map(employee, EmployeeDto.class);
//         }
//         return null;
//     }
    

//     public void deleteEmployee(Long id) {
//         employeeRepositoriy.deleteById(id);
//     }
// }

