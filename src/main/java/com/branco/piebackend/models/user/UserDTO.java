package com.branco.piebackend.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String code;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Long universityId;
}
