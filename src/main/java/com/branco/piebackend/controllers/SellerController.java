package com.branco.piebackend.controllers;

import com.branco.piebackend.models.seller.SellerRegisterDTO;
import com.branco.piebackend.models.seller.SellerResponseDTO;
import com.branco.piebackend.services.SellerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService){
        this.sellerService = sellerService;
    }

    // get all Sellers by their names
    @GetMapping
    public Page<SellerResponseDTO> list(@PageableDefault() Pageable pageable){
        return this.sellerService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showSellerById(@PathVariable Long id){
        Optional<SellerResponseDTO> optionalSeller = this.sellerService.findById(id);
        if(optionalSeller.isPresent()){
            SellerResponseDTO sellerDTO = optionalSeller.get();
            return ResponseEntity.status(HttpStatus.OK).body(sellerDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Seller not found by Id: " + id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SellerRegisterDTO seller, BindingResult result,
                                    Authentication authentication){
        if(result.hasErrors()){
            return validation(result);
        }
        String userCode = authentication.getName();
        SellerResponseDTO saved = this.sellerService.save(seller, userCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    private static ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "The field " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
