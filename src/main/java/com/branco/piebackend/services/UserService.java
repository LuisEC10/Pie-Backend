package com.branco.piebackend.services;

import com.branco.piebackend.entities.UserEntity;
import com.branco.piebackend.models.user.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findById(Long id);

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findAllByUniversity_Id(Pageable pageable, Long universityId);

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> update(UserDTO userDTO, Long id);

    void deleteById(Long id);
}
