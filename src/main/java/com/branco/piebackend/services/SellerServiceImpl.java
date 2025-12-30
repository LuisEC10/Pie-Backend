package com.branco.piebackend.services;

import com.branco.piebackend.entities.SellerEntity;
import com.branco.piebackend.mappers.SellerMapper;
import com.branco.piebackend.models.seller.SellerRegisterDTO;
import com.branco.piebackend.models.seller.SellerResponseDTO;
import com.branco.piebackend.repositories.SellerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService{

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;

    public SellerServiceImpl(SellerRepository sellerRepository, SellerMapper sellerMapper){
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
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
    public SellerResponseDTO save(SellerRegisterDTO sellerEntity) {
        SellerEntity entity = this.sellerMapper.convertToEntity(sellerEntity);
        SellerEntity saved = this.sellerRepository.save(entity);
        return this.sellerMapper.convertToResponseDTO(saved);
    }
}
