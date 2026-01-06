package com.branco.piebackend.controllers;

import com.branco.piebackend.models.product.ProductRegisterDTO;
import com.branco.piebackend.models.product.ProductResponseDTO;
import com.branco.piebackend.models.product.ProductUpdateDTO;
import com.branco.piebackend.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping()
    public Page<ProductResponseDTO> listPageable(@PageableDefault(page = 0) Pageable pageable){
        return this.productService.findAll(pageable);
    }

    @GetMapping( "/{id}")
    public ResponseEntity<?> showProductById(@PathVariable Long id){
        Optional<ProductResponseDTO> optionalProduct = this.productService.findById(id);
        if(optionalProduct.isPresent()){
            ProductResponseDTO productDTO = optionalProduct.get();
            return ResponseEntity.status(HttpStatus.OK).body(productDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Product not found by Id: " + id));
    }

    @GetMapping("/seller/{sellerId}")
    public Page<ProductResponseDTO> listProductsBySeller(@PathVariable Long sellerId, @PageableDefault( page = 0) Pageable pageable){
        return this.productService.findSellerProducts(sellerId, pageable);
    }

    @GetMapping("/name/{name}")
    public Page<ProductResponseDTO> listProductsByName(@PathVariable String name, @PageableDefault(page = 0) Pageable pageable){
        return this.productService.findByProductName(name, pageable);
    }

    @GetMapping("/stock/{stock}")
    public Page<ProductResponseDTO> listProductsByStock(@PathVariable Integer stock, @PageableDefault(page = 0) Pageable pageable){
        return this.productService.findByStock(stock, pageable);
    }

    @GetMapping("/seller/{sellerId}/stock/{stock}")
    public Page<ProductResponseDTO> listProductsBySellerAndStock(@PathVariable Long sellerId, @PathVariable Integer stock,
                                                                 @PageableDefault(page = 0) Pageable pageable) {
        return this.productService.findBySellerAndStock(sellerId, stock, pageable);
    }

    // By roles

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> create(@Valid @RequestBody ProductRegisterDTO product, BindingResult result,
                                    Authentication authentication){
        if(result.hasErrors()){
            return validation(result);
        }
        ProductResponseDTO saved = this.productService.save(product, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> update(@Valid @RequestBody ProductUpdateDTO product, BindingResult result,
                                    @PathVariable Long id, Authentication authentication){
        if(result.hasErrors()){
            return validation(result);
        }

        Optional<ProductResponseDTO> optionalProduct = this.productService.update(product, id, authentication.getName());
        if(optionalProduct.isPresent()){
            ProductResponseDTO productDTO = optionalProduct.get();
            return ResponseEntity.ok(productDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication){
        this.productService.deleteById(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    private static ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "The field " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
