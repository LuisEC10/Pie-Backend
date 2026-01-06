package com.branco.piebackend.models.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private Long id;

    private Long productId;
    private String productName;
    private String productImageURL;

    private Integer quantity;
    private Double priceAtPurchase;
    private Double subtotal;
}
