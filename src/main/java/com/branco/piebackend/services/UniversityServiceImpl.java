package com.branco.piebackend.services;

import com.branco.piebackend.entities.UniversityEntity;
import com.branco.piebackend.mappers.UniversityMapper;
import com.branco.piebackend.models.university.UniversityRegisterDTO;
import com.branco.piebackend.models.university.UniversityResponseDTO;
import com.branco.piebackend.models.university.UniversityUpdateDTO;
import com.branco.piebackend.repositories.UniversityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityServiceImpl implements UniversityService{

    private final UniversityRepository universityRepository;
    private final UniversityMapper universityMapper;

    public UniversityServiceImpl(UniversityRepository universityRepository, UniversityMapper universityMapper){
        this.universityRepository = universityRepository;
        this.universityMapper = universityMapper;
    }

   @Override
    @Transactional(readOnly = true)
    public Optional<UniversityResponseDTO> findById(Long id) {
        return this.universityRepository.findById(id)
                .map(this.universityMapper::convertToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UniversityResponseDTO> findAllByIds(List<Long> ids) {
        return this.universityRepository.findAllById(ids)
                .stream()
                .map(this.universityMapper::convertToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UniversityResponseDTO> findAll() {
        return this.universityRepository.findAll()
                .stream()
                .map(this.universityMapper::convertToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public UniversityResponseDTO save(UniversityRegisterDTO universityRegisterDTO) {
        UniversityEntity entity = this.universityMapper.convertToEntity(universityRegisterDTO);
        UniversityEntity saved = this.universityRepository.save(entity);
        return this.universityMapper.convertToResponseDTO(saved);
    }

    @Override
    @Transactional
    public Optional<UniversityResponseDTO> update(UniversityUpdateDTO universityUpdateDTO, Long id) {
        Optional<UniversityEntity> optionalUniversity = this.universityRepository.findById(id);
        if(optionalUniversity.isPresent()){
            UniversityEntity universityDB = optionalUniversity.get();
            this.universityMapper.updateUniversityFromDTO(universityUpdateDTO, universityDB);
            return Optional.of(this.universityMapper.convertToResponseDTO(this.universityRepository.save(universityDB)));
        }
        return Optional.empty();
    }
}
