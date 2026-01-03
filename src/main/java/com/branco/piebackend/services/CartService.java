package com.branco.piebackend.services;


import com.branco.piebackend.models.cart.CartItemRequestDTO;
import com.branco.piebackend.models.cart.CartResponseDTO;

import java.util.Optional;

public interface CartService {
    Optional<CartResponseDTO> addItemToCart(String userCode, CartItemRequestDTO request);

    Optional<CartResponseDTO> removeItemFromCart(String userCode, Long cartItemId);

    Optional<CartResponseDTO> getCartByUser(String userCode);

    void clearCart(String userCode);
}
