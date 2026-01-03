package com.branco.piebackend.mappers;

import com.branco.piebackend.entities.CartItemEntity;
import com.branco.piebackend.models.cart.CartItemRequestDTO;
import com.branco.piebackend.models.cart.CartItemResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItemResponseDTO convertToResponseDTO(CartItemEntity entity){
        return CartItemResponseDTO.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .subTotal(entity.getTotal())
                .productName(entity.getProduct().getName())
                .productImageUrl(entity.getProduct().getImageURL())
                .productPrice(entity.getProduct().getPrice())
                .productId(entity.getProduct().getId())
                .build();
    }

    public CartItemResponseDTO convertToResponseDTO(CartItemRequestDTO request){
        return CartItemResponseDTO.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();
    }
}
