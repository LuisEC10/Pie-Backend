package com.branco.piebackend.services;

import com.branco.piebackend.entities.SellerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    Optional<SellerEntity> getById(Long id);

    List<SellerEntity> findAll();

    Page<SellerEntity> findAll(Pageable pageable);

    SellerEntity save(SellerEntity sellerEntity);
}
