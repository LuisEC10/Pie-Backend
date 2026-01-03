package com.branco.piebackend.controllers;

import com.branco.piebackend.entities.UserEntity;
import com.branco.piebackend.models.cart.CartItemRequestDTO;
import com.branco.piebackend.repositories.UserRepository;
import com.branco.piebackend.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository){
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getMyCart(Authentication authentication){
        Long userId = this.getUserIdFromAuth(authentication);
        return ResponseEntity.of(this.cartService.getCartByUser(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemRequestDTO request, Authentication authentication) {
        Long userId = this.getUserIdFromAuth(authentication);
        return ResponseEntity.of(this.cartService.addItemToCart(userId, request));
    }

    @DeleteMapping()
    public ResponseEntity<Void> clearCart(Authentication authentication){
        Long userId = this.getUserIdFromAuth(authentication);
        this.cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    private Long getUserIdFromAuth(Authentication authentication) {
        String code = authentication.getName();
        UserEntity user = userRepository.findUserByCode(code)
                .orElseThrow(() -> new RuntimeException("User not found in BD"));
        return user.getId();
    }
}
