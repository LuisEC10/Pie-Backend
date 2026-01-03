package com.branco.piebackend.services;

import com.branco.piebackend.models.seller.SellerRegisterDTO;
import com.branco.piebackend.models.seller.SellerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SellerService {
    Optional<SellerResponseDTO> findById(Long id);

    Page<SellerResponseDTO> findAll(Pageable pageable);

    SellerResponseDTO save(SellerRegisterDTO dto, String userCode);
}
