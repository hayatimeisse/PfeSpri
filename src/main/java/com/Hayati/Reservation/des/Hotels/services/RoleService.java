package com.Hayati.Reservation.des.Hotels.services;



import com.Hayati.Reservation.des.Hotels.dto.ChambreDto;
import com.Hayati.Reservation.des.Hotels.dto.EmployeeDto;
import com.Hayati.Reservation.des.Hotels.dto.HotelDto;
import com.Hayati.Reservation.des.Hotels.dto.RoleDto;
import com.Hayati.Reservation.des.Hotels.entity.Chambre;
import com.Hayati.Reservation.des.Hotels.entity.Employee;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;
import com.Hayati.Reservation.des.Hotels.entity.Role;
import com.Hayati.Reservation.des.Hotels.repositoriy.ChambreRepositoriy;
import com.Hayati.Reservation.des.Hotels.repositoriy.EmployeeRepositoriy;
import com.Hayati.Reservation.des.Hotels.repositoriy.HotelRepositoriy;
import com.Hayati.Reservation.des.Hotels.repositoriy.RoleRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

   

    @Autowired
    private ModelMapper modelMapper;


   
    public RoleDto createRole(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
        role = roleRepository.save(role);
        return modelMapper.map(role, RoleDto.class);
    }

    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> modelMapper.map(roleRepository, RoleDto.class))
                .collect(Collectors.toList());
    }

    public RoleDto getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(role -> modelMapper.map(roleRepository, RoleDto.class))
                .orElse(null);
    }

    public RoleDto updateRole(Long id, RoleDto roleDto) {
        return roleRepository.findById(id)
                .map(existingRole-> {
                    // Update the attributes of the existingHotel object with the values from hotelDto
                    existingRole.setName(roleDto.getName());
                    
                    
                    // Save the updated hotel entity in the repository
                    roleRepository.save(existingRole);
                    
                    // Map the updated hotel entity to a HotelDto object and return it
                    return modelMapper.map(existingRole, RoleDto.class);
                })
                .orElse(null);
    }
    
    

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}

