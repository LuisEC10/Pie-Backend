package com.branco.piebackend.controllers;

import com.branco.piebackend.models.university.UniversityRegisterDTO;
import com.branco.piebackend.models.university.UniversityResponseDTO;
import com.branco.piebackend.models.university.UniversityUpdateDTO;
import com.branco.piebackend.services.UniversityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(UniversityService universityService){
        this.universityService = universityService;
    }

    @GetMapping
    public List<UniversityResponseDTO> list(){
        return this.universityService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showUniversityById(@PathVariable Long id){
        Optional<UniversityResponseDTO> optionalUniversity = this.universityService.findById(id);
        if(optionalUniversity.isPresent()) {
            UniversityResponseDTO universityDTO = optionalUniversity.get();
            return ResponseEntity.status(HttpStatus.OK).body(universityDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "University not found by Id:" + id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UniversityRegisterDTO university, BindingResult result) {
        if(result.hasErrors()){
            return validation(result);
        }
        UniversityResponseDTO saved = this.universityService.save(university);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UniversityUpdateDTO university, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validation(result);
        }

        Optional<UniversityResponseDTO> optionalUniversity = this.universityService.update(university, id);
        if(optionalUniversity.isPresent()){
            UniversityResponseDTO universityDTO = optionalUniversity.get();
            return ResponseEntity.ok(universityDTO);
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "The field " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
