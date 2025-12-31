package com.branco.piebackend.controllers;

import com.branco.piebackend.models.user.UserRegisterDTO;
import com.branco.piebackend.models.user.UserResponseDTO;
import com.branco.piebackend.models.user.UserUpdateDTO;
import com.branco.piebackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public Page<UserResponseDTO> listPageable(@PageableDefault Pageable pageable){
        return this.userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showUserById(@PathVariable Long id){
        Optional<UserResponseDTO> optionalUser = this.userService.findById(id);
        if(optionalUser.isPresent()){
            UserResponseDTO userDTO = optionalUser.get();
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "User not found by Id: " + id));
    }

    @GetMapping("/university/{universityId}")
    public Page<UserResponseDTO> showUsersByUniversityId(@PageableDefault Pageable pageable, @PathVariable Long universityId){
        return this.userService.findAllByUniversity_Id(pageable, universityId);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserRegisterDTO user, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        UserResponseDTO saved = this.userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateDTO user, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validation(result);
        }
        Optional<UserResponseDTO> optionalUser = this.userService.update(user, id);
        if (optionalUser.isPresent()){
            UserResponseDTO userDTO = optionalUser.get();
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<UserResponseDTO> user = this.userService.findById(id);
        if(user.isPresent()){
            this.userService.deleteById(id);
            return ResponseEntity.noContent().build();
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
