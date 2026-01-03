package com.branco.piebackend.mappers;

import com.branco.piebackend.entities.ProductEntity;
import com.branco.piebackend.entities.SellerEntity;
import com.branco.piebackend.entities.UniversityEntity;
import com.branco.piebackend.entities.UserEntity;
import com.branco.piebackend.models.seller.SellerRegisterDTO;
import com.branco.piebackend.models.seller.SellerResponseDTO;
import com.branco.piebackend.repositories.UniversityRepository;
import com.branco.piebackend.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SellerMapper {

    public SellerResponseDTO convertToResponseDTO(SellerEntity entity){
        return SellerResponseDTO.builder()
                .brand(entity.getBrand())
                .id(entity.getId())
                .ownerId(entity.getOwner() != null ? entity.getOwner().getId() : null)
                .universities(entity.getUniversities().stream().map(UniversityEntity::getId).toList())
                .deliveries(entity.getDeliveries().stream().map(UserEntity::getId).toList())
                .products(entity.getProducts() == null ? new ArrayList<>(): entity.getProducts().stream().map(ProductEntity::getId).toList())
                .build();
    }

    public SellerEntity convertToEntity(SellerRegisterDTO dto){
        return SellerEntity.builder()
                .brand(dto.getBrand())
                .build();
    }
}
