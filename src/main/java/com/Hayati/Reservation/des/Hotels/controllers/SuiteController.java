package com.Hayati.Reservation.des.Hotels.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hayati.Reservation.des.Hotels.dto.SuiteDto;
import com.Hayati.Reservation.des.Hotels.services.SuiteService;

@RestController
@RequestMapping("/api/auth/Suite")
public class SuiteController {

    @Autowired
    private SuiteService suiteService;

    @PostMapping
    public ResponseEntity<SuiteDto> createSuite(@RequestBody SuiteDto suiteDto) {
        SuiteDto createdSuite = suiteService.createSuite(suiteDto);
        return ResponseEntity.ok(createdSuite);
    }

    @GetMapping
    public ResponseEntity<List<SuiteDto>> getAllSuites() {
        List<SuiteDto> suites = suiteService.getAllSuites();
        return ResponseEntity.ok(suites);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuiteDto> getSuiteById(@PathVariable Long id) {
        SuiteDto suite = suiteService.getSuiteById(id);
        return suite != null ? ResponseEntity.ok(suite) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuiteDto> updateSuite(@PathVariable Long id, @RequestBody SuiteDto suiteDto) {
        SuiteDto updatedSuite = suiteService.updateSuite(id, suiteDto);
        return updatedSuite != null ? ResponseEntity.ok(updatedSuite) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuite(@PathVariable Long id) {
        suiteService.deleteSuite(id);
        return ResponseEntity.noContent().build();
    }
    
}

