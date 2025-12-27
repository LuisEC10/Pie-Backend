package com.branco.piebackend.services;

import com.branco.piebackend.entities.UserEntity;
import com.branco.piebackend.models.user.UserDTO;
import com.branco.piebackend.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserEntity> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserEntity> findAllByUniversity_Id(Pageable pageable, Long universityId) {
        return this.userRepository.findAllByUniversity_Id(pageable, universityId);
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity userEntity) {
        return this.userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public Optional<UserEntity> update(UserDTO userDTO, Long id) {
        Optional<UserEntity> optionalUser = this.userRepository.findById(id);
        if(optionalUser.isPresent()) {
            UserEntity userDB = optionalUser.get();
            userDB.setCode(userDTO.getCode());
            userDB.setEmail(userDTO.getEmail());
            userDB.setFirstName(userDTO.getFirstName());
            userDB.setLastName(userDTO.getLastName());
            userDB.setPhoneNumber(userDTO.getPhoneNumber());
            return Optional.of(this.userRepository.save(userDB));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // Implement update for soft delete
    }

}
