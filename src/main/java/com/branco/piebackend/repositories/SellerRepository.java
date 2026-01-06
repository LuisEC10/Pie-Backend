package com.branco.piebackend.repositories;

import com.branco.piebackend.entities.SellerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
    Page<SellerEntity> findAll(Pageable page);

    Optional<SellerEntity> findByOwner_Code(String userCode);
}
