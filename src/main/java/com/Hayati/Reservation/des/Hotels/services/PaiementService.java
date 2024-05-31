package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.PaiementDto;
import com.Hayati.Reservation.des.Hotels.entity.Paiement;
import com.Hayati.Reservation.des.Hotels.repositoriy.PaiementRepositoriy;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepositoriy paiementRepositoriy;

    @Autowired
    private ModelMapper modelMapper;

    public PaiementDto createPaiement(PaiementDto paiementDto) {
        Paiement paiement = modelMapper.map(paiementDto, Paiement.class);
        paiement = paiementRepositoriy.save(paiement);
        return modelMapper.map(paiement, PaiementDto.class);
    }

    public List<PaiementDto> getAllPaiements() {
        return paiementRepositoriy.findAll()
                .stream()
                .map(role -> modelMapper.map(paiementRepositoriy, PaiementDto.class))
                .collect(Collectors.toList());
    }

    public PaiementDto getPaiementById(Long id) {
        return paiementRepositoriy.findById(id)
                .map(role -> modelMapper.map(paiementRepositoriy, PaiementDto.class))
                .orElse(null);
    }

    public PaiementDto updatePaiement(Long id, PaiementDto paiementDto) {
        return paiementRepositoriy.findById(id)
                .map(existingPaiement -> {
                    // Mise à jour des attributs de l'objet existingPaiement avec les valeurs de paiementDto
                    existingPaiement.setMontant(paiementDto.getMontant());
                    existingPaiement.setDatepaiement(paiementDto.getDatepaiement());
                    existingPaiement.setMethodepaiement(paiementDto.getMethodepaiement());
                    // Enregistrement des modifications dans le repository
                    paiementRepositoriy.save(existingPaiement);
                    // Mapper l'objet existingPaiement en objet PaiementDto et le renvoyer
                    return modelMapper.map(existingPaiement, PaiementDto.class);
                })
                .orElse(null);
    }
    

    public void deletePaiement(Long id) {
        paiementRepositoriy.deleteById(id);
    }
}
