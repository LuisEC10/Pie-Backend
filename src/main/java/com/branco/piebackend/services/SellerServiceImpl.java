package com.branco.piebackend.services;

import com.branco.piebackend.entities.Role;
import com.branco.piebackend.entities.SellerEntity;
import com.branco.piebackend.entities.UniversityEntity;
import com.branco.piebackend.entities.UserEntity;
import com.branco.piebackend.mappers.SellerMapper;
import com.branco.piebackend.models.seller.SellerRegisterDTO;
import com.branco.piebackend.models.seller.SellerResponseDTO;
import com.branco.piebackend.repositories.RoleRepository;
import com.branco.piebackend.repositories.SellerRepository;
import com.branco.piebackend.repositories.UniversityRepository;
import com.branco.piebackend.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService{

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;
    private final UniversityRepository universityRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public SellerServiceImpl(SellerRepository sellerRepository, SellerMapper sellerMapper, UserRepository userRepository,
                             UniversityRepository universityRepository, RoleRepository roleRepository){
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
        this.universityRepository = universityRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SellerResponseDTO> findById(Long id) {
        return this.sellerRepository.findById(id)
                .map(this.sellerMapper::convertToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SellerResponseDTO> findAll(Pageable pageable) {
        return this.sellerRepository.findAll(pageable)
                .map(this.sellerMapper::convertToResponseDTO);
    }

    @Override
    @Transactional
    public SellerResponseDTO save(SellerRegisterDTO dto, String userCode) {
        UserEntity owner = this.userRepository.findUserByCode(userCode).orElseThrow();
        List<UniversityEntity> universities = this.universityRepository.findAllById(dto.getUniversities());
        Role sellerRole = this.roleRepository.findByName("ROLE_SELLER").orElseThrow();

        if(!owner.getRoles().contains(sellerRole)){
            owner.getRoles().add(sellerRole);
            this.userRepository.save(owner);
        }

        if(universities.isEmpty()){
            throw new RuntimeException("Debes seleccionar una universidad");
        }
        SellerEntity seller = this.sellerMapper.convertToEntity(dto);
        seller.setOwner(owner);
        seller.setUniversities(universities);

        SellerEntity saved = this.sellerRepository.save(seller);
        return this.sellerMapper.convertToResponseDTO(saved);
    }
}
