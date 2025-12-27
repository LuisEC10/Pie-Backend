package com.branco.piebackend.models.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private Long sellerId;
    private String description;
    private String imageURL;
}
