package com.branco.piebackend.mappers;

import com.branco.piebackend.entities.ProductEntity;
import com.branco.piebackend.entities.UniversityEntity;
import com.branco.piebackend.models.university.UniversityRegisterDTO;
import com.branco.piebackend.models.university.UniversityResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UniversityMapper {

    public UniversityResponseDTO convertToResponseDTO(UniversityEntity universityEntity){
        return UniversityResponseDTO.builder()
                .id(universityEntity.getId())
                .name(universityEntity.getName())
                .alias(universityEntity.getAlias())
                .emailDomain(universityEntity.getEmailDomain())
                .centerLatitude(universityEntity.getCenterLatitude())
                .centerLongitude(universityEntity.getCenterLongitude())
                .build();
    }

    public UniversityEntity convertToEntity(UniversityRegisterDTO productEntity){
        return UniversityEntity.builder()
                .name(productEntity.getName())
                .alias(productEntity.getAlias())
                .centerLatitude(productEntity.getCenterLatitude())
                .centerLongitude(productEntity.getCenterLongitude())
                .emailDomain(productEntity.getEmailDomain())
                .build();
    }

    public void updateUniversityFromDTO(UniversityRegisterDTO dto, UniversityEntity entity){
        if (dto == null || entity == null) return;

        entity.setName(dto.getName());
        entity.setAlias(dto.getAlias());
        entity.setCenterLatitude(dto.getCenterLatitude());
        entity.setCenterLongitude(dto.getCenterLongitude());
        entity.setEmailDomain(dto.getEmailDomain());
    }
}
