package com.branco.piebackend.models.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDTO {
    private Long id;
    private Integer quantity;
    private Double subTotal;
    private Long productId;
    private String productName;
    private String productImageUrl;
    private Double productPrice;
}
