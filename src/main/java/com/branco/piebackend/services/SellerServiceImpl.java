package com.branco.piebackend.services;

import com.branco.piebackend.entities.SellerEntity;
import com.branco.piebackend.repositories.SellerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService{

    private final SellerRepository sellerRepository;

    public SellerServiceImpl(SellerRepository sellerRepository){
        this.sellerRepository = sellerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SellerEntity> getById(Long id) {
        return this.sellerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SellerEntity> findAll() {
        return this.sellerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SellerEntity> findAll(Pageable pageable) {
        return this.sellerRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public SellerEntity save(SellerEntity sellerEntity) {
        return this.sellerRepository.save(sellerEntity);
    }
}
