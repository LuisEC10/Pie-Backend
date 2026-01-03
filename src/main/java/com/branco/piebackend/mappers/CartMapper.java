package com.branco.piebackend.mappers;

import com.branco.piebackend.entities.CartEntity;
import com.branco.piebackend.models.cart.CartResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CartMapper {

    private final CartItemMapper cartItemMapper;

    public CartMapper(CartItemMapper cartItemMapper){
        this.cartItemMapper = cartItemMapper;
    }

    public CartResponseDTO convertToResponseDTO(CartEntity entity){
        return CartResponseDTO.builder()
                .id(entity.getId())
                .total(entity.getTotal())
                .totalItems(entity.getItems() == null ? 0 : entity.getItems().size())
                .items(entity.getItems() == null ?
                        new ArrayList<>() :
                        entity.getItems().stream().map(this.cartItemMapper::convertToResponseDTO).toList())
                .build();
    }

    public CartEntity convertToEntity(CartResponseDTO response){
        return CartEntity.builder()
                .id(response.getId())
                .total(response.getTotal())
                .items(response.getItems().stream().map(this.cartItemMapper::convertToEntity).toList())
                .build();
    }
}
