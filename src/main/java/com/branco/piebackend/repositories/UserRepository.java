package com.branco.piebackend.repositories;

import com.branco.piebackend.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserByCode(String code);

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findAllByUniversity_Id(Pageable pageable, Long universityId);

    boolean existsByEmail(String email);
}
