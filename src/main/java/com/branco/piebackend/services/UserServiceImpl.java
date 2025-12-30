package com.branco.piebackend.services;

import com.branco.piebackend.entities.UserEntity;
import com.branco.piebackend.mappers.UserMapper;
import com.branco.piebackend.models.user.UserRegisterDTO;
import com.branco.piebackend.models.user.UserResponseDTO;
import com.branco.piebackend.models.user.UserUpdateDTO;
import com.branco.piebackend.repositories.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> findById(@NotNull Long id) {
        return this.userRepository.findById(id)
                .map(this.userMapper::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable)
                .map(this.userMapper::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAllByUniversity_Id(Pageable pageable,Long universityId) {
        return this.userRepository.findAllByUniversity_Id(pageable, universityId)
                .map(this.userMapper::convertToDTO);
    }

    @Override
    @Transactional
    public UserResponseDTO save(UserRegisterDTO userRegisterDTO) {
        UserEntity entity = this.userMapper.convertToEntity(userRegisterDTO);
        entity.setPassword(this.passwordEncoder.encode(userRegisterDTO.getPassword()));
        UserEntity saved = this.userRepository.save(entity);
        return this.userMapper.convertToDTO(saved);
    }

    @Override
    @Transactional
    public Optional<UserResponseDTO> update(UserUpdateDTO userDTO, Long id) {
        Optional<UserEntity> optionalUser = this.userRepository.findById(id);
        if(optionalUser.isPresent()) {
            UserEntity userDB = optionalUser.get();
            this.userMapper.updateUserFromDTO(userDTO, userDB);
            return Optional.of(this.userMapper.convertToDTO(this.userRepository.save(userDB)));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        UserEntity user = this.userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(false);
        this.userRepository.save(user);
    }
}
