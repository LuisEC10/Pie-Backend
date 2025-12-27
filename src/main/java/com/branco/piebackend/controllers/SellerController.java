package com.branco.piebackend.controllers;

import com.branco.piebackend.entities.SellerEntity;
import com.branco.piebackend.entities.UniversityEntity;
import com.branco.piebackend.models.seller.SellerDTO;
import com.branco.piebackend.services.SellerService;
import com.branco.piebackend.services.UniversityService;
import com.branco.piebackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {
    private final SellerService sellerService;
    private final UserService userService;
    private final UniversityService universityService;

    public SellerController(SellerService sellerService,
                            UserService userService,
                            UniversityService universityService){
        this.sellerService = sellerService;
        this.userService = userService;
        this.universityService = universityService;
    }

    // get all Sellers by their names
    @GetMapping
    public List<SellerDTO> list(){
        return this.sellerService.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @GetMapping("/page/{page}")
    public List<SellerDTO> list(@PathVariable Integer page){
        Pageable pageable = Pageable.ofSize(10);
        return this.sellerService.findAll(pageable)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SellerDTO seller, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        SellerEntity entity = convertToEntity(seller);
        SellerEntity saved = this.sellerService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(saved));
    }

    private SellerDTO convertToDTO(SellerEntity sellerEntity){
        return SellerDTO.builder()
                .brand(sellerEntity.getBrand())
                .id(sellerEntity.getId())
                .universities(sellerEntity.getUniversities()
                        .stream()
                        .map(UniversityEntity::getId)
                        .toList())
                .ownerId(sellerEntity.getOwner().getId())
                .build();
    }

    private SellerEntity convertToEntity(SellerDTO dto){
        List<UniversityEntity> universities = this.universityService.findAllByIds(dto.getUniversities());

        return SellerEntity.builder()
                .brand(dto.getBrand())
                .id(dto.getId())
                .owner(this.userService.findById(dto.getOwnerId()).orElseThrow())
                .universities(universities)
                .build();
    }

    private static ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "The field " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
