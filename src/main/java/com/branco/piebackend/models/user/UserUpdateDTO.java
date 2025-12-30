package com.branco.piebackend.models.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private String phoneNumber;

}
