package com.branco.piebackend.controllers;

import com.branco.piebackend.entities.UniversityEntity;
import com.branco.piebackend.models.university.UniversityDTO;
import com.branco.piebackend.services.UniversityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(UniversityService universityService){
        this.universityService = universityService;
    }

    @GetMapping
    public List<UniversityDTO> list(){
        return this.universityService.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showUniversityById(@PathVariable Long id){
        Optional<UniversityEntity> optionalUniversity = this.universityService.findById(id);
        if(optionalUniversity.isPresent()) {
            UniversityDTO universityDTO = convertToDTO(optionalUniversity.orElseThrow());
            return ResponseEntity.status(HttpStatus.OK).body(universityDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "University not found by Id:" + id));
    }

    private UniversityDTO convertToDTO(UniversityEntity entity){
        return UniversityDTO.builder()
                .id(entity.getId())
                .alias(entity.getAlias())
                .name(entity.getName())
                .emailDomain(entity.getEmailDomain())
                .centerLatitude(entity.getCenterLatitude())
                .centerLongitude(entity.getCenterLongitude())
                .build();
    }


}
