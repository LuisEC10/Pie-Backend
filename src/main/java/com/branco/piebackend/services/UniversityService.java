package com.branco.piebackend.services;

import com.branco.piebackend.entities.UniversityEntity;

import java.util.List;
import java.util.Optional;

public interface UniversityService {
    Optional<UniversityEntity> findById(Long id);

    List<UniversityEntity> findAllByIds(List<Long> id);

    List<UniversityEntity> findAll();
}
