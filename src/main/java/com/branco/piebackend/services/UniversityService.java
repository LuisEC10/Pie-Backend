package com.branco.piebackend.services;

import com.branco.piebackend.entities.UniversityEntity;
import com.branco.piebackend.models.university.UniversityResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UniversityService {
    Optional<UniversityResponseDTO> findById(Long id);

    List<UniversityResponseDTO> findAllByIds(List<Long> ids);

    List<UniversityResponseDTO> findAll();
}
