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

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getMyCart(Authentication authentication){
        String userCode = authentication.getName();
        return ResponseEntity.of(this.cartService.getCartByUser(userCode));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemRequestDTO request, Authentication authentication) {
        String userCode = authentication.getName();
        return ResponseEntity.of(this.cartService.addItemToCart(userCode, request));
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long cartItemId, Authentication authentication) {
        String userCode = authentication.getName();
        return ResponseEntity.of(this.cartService.removeItemFromCart(userCode, cartItemId));
    }

    @DeleteMapping()
    public ResponseEntity<Void> clearCart(Authentication authentication){
        String userCode = authentication.getName();
        this.cartService.clearCart(userCode);
        return ResponseEntity.noContent().build();
    }


}
