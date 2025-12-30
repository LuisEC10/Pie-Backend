package com.branco.piebackend.mappers;

import com.branco.piebackend.entities.Role;
import com.branco.piebackend.entities.UserEntity;
import com.branco.piebackend.models.user.UserRegisterDTO;
import com.branco.piebackend.models.user.UserResponseDTO;
import com.branco.piebackend.models.user.UserUpdateDTO;
import com.branco.piebackend.repositories.RoleRepository;
import com.branco.piebackend.repositories.UniversityRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {

    private final UniversityRepository universityRepository;
    private final RoleRepository roleRepository;

    public UserMapper(UniversityRepository universityRepository, RoleRepository roleRepository){
        this.universityRepository = universityRepository;
        this.roleRepository = roleRepository;
    }

    public void updateUserFromDTO(UserUpdateDTO dto, UserEntity entity){
        if(dto == null || entity == null) return;

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
    }

    public UserResponseDTO convertToDTO(UserEntity entity){
        return UserResponseDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phoneNumber(entity.getPhoneNumber())
                .universityId(entity.getUniversity().getId())
                .roles(entity.getRoles().stream().map(Role::getName).toList())
                .build();
    }

    public UserEntity convertToEntity(UserRegisterDTO dto) {
        return UserEntity.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .university(this.universityRepository.findById(dto.getUniversityId()).orElseThrow())
                .roles(this.getRoles())
                .enabled(true)
                .build();
    }

    private List<Role> getRoles(){
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRole = this.roleRepository.findByName("ROLE_USER");
        optionalRole.ifPresent(roles::add);
        return roles;
    }
}
