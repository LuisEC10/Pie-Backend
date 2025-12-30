package com.branco.piebackend.services;

import com.branco.piebackend.models.university.UniversityRegisterDTO;
import com.branco.piebackend.models.university.UniversityResponseDTO;
import com.branco.piebackend.models.university.UniversityUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface UniversityService {
    Optional<UniversityResponseDTO> findById(Long id);

    List<UniversityResponseDTO> findAllByIds(List<Long> ids);

    List<UniversityResponseDTO> findAll();

    UniversityResponseDTO save(UniversityRegisterDTO universityRegisterDTO);

    Optional<UniversityResponseDTO> update(UniversityUpdateDTO universityUpdateDTO, Long id);

    // must be soft because of sellers table
    //void deleteById(Long id);
}
