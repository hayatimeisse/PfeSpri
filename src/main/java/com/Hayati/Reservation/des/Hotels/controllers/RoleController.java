package com.Hayati.Reservation.des.Hotels.controllers;



import com.Hayati.Reservation.des.Hotels.dto.ChambreDto;
import com.Hayati.Reservation.des.Hotels.dto.PaiementDto;
import com.Hayati.Reservation.des.Hotels.dto.RoleDto;
import com.Hayati.Reservation.des.Hotels.services.ChambreService;
import com.Hayati.Reservation.des.Hotels.services.PaiementService;
import com.Hayati.Reservation.des.Hotels.services.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/Role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        RoleDto createdRole = roleService.createRole(roleDto);
        return ResponseEntity.ok(createdRole);
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        RoleDto role = roleService.getRoleById(id);
        return role != null ? ResponseEntity.ok(role) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        RoleDto updatedRole = roleService.updateRole(id, roleDto);
        return updatedRole != null ? ResponseEntity.ok(updatedRole) : ResponseEntity.notFound().build();
    }
  

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}

