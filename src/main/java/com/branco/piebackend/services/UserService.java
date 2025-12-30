package com.branco.piebackend.services;

import com.branco.piebackend.models.user.UserRegisterDTO;
import com.branco.piebackend.models.user.UserResponseDTO;
import com.branco.piebackend.models.user.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    Optional<UserResponseDTO> findById(Long id);

    Page<UserResponseDTO> findAll(Pageable pageable);

    Page<UserResponseDTO> findAllByUniversity_Id(Pageable pageable, Long universityId);

    UserResponseDTO save(UserRegisterDTO userRegisterDTO);

    Optional<UserResponseDTO> update(UserUpdateDTO userDTO, Long id);

    // soft delete
    void deleteById(Long id);
}
