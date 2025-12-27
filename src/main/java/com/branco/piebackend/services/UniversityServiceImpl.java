package com.branco.piebackend.services;

import com.branco.piebackend.entities.UniversityEntity;
import com.branco.piebackend.repositories.UniversityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityServiceImpl implements UniversityService{

    private final UniversityRepository universityRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository){
        this.universityRepository = universityRepository;
    }

   @Override
    @Transactional(readOnly = true)
    public Optional<UniversityEntity> findById(Long id) {
        return this.universityRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UniversityEntity> findAllByIds(List<Long> id) {
        return this.universityRepository.findAllById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UniversityEntity> findAll() {
        return this.universityRepository.findAll();
    }
}
