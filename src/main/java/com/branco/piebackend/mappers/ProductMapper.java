package com.branco.piebackend.mappers;

import com.branco.piebackend.entities.ProductEntity;
import com.branco.piebackend.models.product.ProductRegisterDTO;
import com.branco.piebackend.models.product.ProductResponseDTO;
import com.branco.piebackend.models.product.ProductUpdateDTO;
import com.branco.piebackend.services.SellerService;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final SellerService sellerService;

    public ProductMapper(SellerService sellerService){
        this.sellerService = sellerService;
    }

    public void updateProductFromDTO(ProductUpdateDTO dto, ProductEntity entity){
        if (dto == null || entity == null) return;

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        entity.setImageURL(dto.getImageURL());
    }

    public ProductEntity convertToEntity(ProductRegisterDTO dto){
        return ProductEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .stock(dto.getStock())
                .price(dto.getPrice())
                .imageURL(dto.getImageURL())
                .seller(this.sellerService.getById(dto.getSellerId()).orElseThrow())
                .build();
    }

    public ProductResponseDTO convertToResponseDTO(ProductEntity product){
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .stock(product.getStock())
                .price(product.getPrice())
                .imageURL(product.getImageURL())
                .sellerId(product.getSeller().getId())
                .build();
    }

    public ProductRegisterDTO convertToRegisterDTO(ProductEntity product){
        return ProductRegisterDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .stock(product.getStock())
                .price(product.getPrice())
                .imageURL(product.getImageURL())
                .sellerId(product.getSeller().getId())
                .build();
    }
}
