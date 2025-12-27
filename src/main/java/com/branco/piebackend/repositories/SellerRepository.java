package com.branco.piebackend.repositories;

import com.branco.piebackend.entities.SellerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
    Page<SellerEntity> findAll(Pageable page);

}
