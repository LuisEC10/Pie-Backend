package com.branco.piebackend.repositories;


import com.branco.piebackend.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // To see all products
    Page<ProductEntity> findAll(Pageable pageable);

    // See all products by seller
    Page<ProductEntity> findAllBySeller_Id(Long sellerId, Pageable page);

    // by product's name
    Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable page);

    // Products with stock
    Page<ProductEntity> findByStockGreaterThan(Integer stock, Pageable page);

    // available by seller and stock
    Page<ProductEntity> findBySeller_IdAndStockGreaterThan(Long sellerId, Integer stock, Pageable page);
}
