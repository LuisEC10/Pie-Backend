package com.branco.piebackend.services;


import com.branco.piebackend.models.cart.CartItemRequestDTO;
import com.branco.piebackend.models.cart.CartResponseDTO;

import java.util.Optional;

public interface CartService {
    Optional<CartResponseDTO> addItemToCart(Long userId, CartItemRequestDTO request);

    Optional<CartResponseDTO> removeItemFromCart(Long userId, Long cartItemId);

    Optional<CartResponseDTO> getCartByUser(Long userId);

    void clearCart(Long userId);
}
