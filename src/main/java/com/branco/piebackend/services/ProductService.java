package com.branco.piebackend.services;

import com.branco.piebackend.entities.ProductEntity;
import com.branco.piebackend.models.product.ProductRegisterDTO;
import com.branco.piebackend.models.product.ProductResponseDTO;
import com.branco.piebackend.models.product.ProductUpdateDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductResponseDTO> findAll();

    // To see all products
    Page<ProductResponseDTO> findAll(Pageable pageable);

    // See all products by seller
    Page<ProductResponseDTO> findSellerProducts(Long sellerId, Pageable pageable);

    // by product's name
    Page<ProductResponseDTO> findByProductName(String name, Pageable pageable);

    // Products with stock
    Page<ProductResponseDTO> findByStock(Integer stock, Pageable pageable);

    Optional<ProductResponseDTO> findById(@NotNull Long id);

    // available by seller and stock
    Page<ProductResponseDTO> findBySellerAndStock(Long sellerId, Integer stock, Pageable pageable);

    ProductResponseDTO save(ProductRegisterDTO productRegisterDTO, String userCode);

    Optional<ProductResponseDTO> update(ProductUpdateDTO product, Long id, String userCode);

    void deleteById(Long id, String userCode);

}
