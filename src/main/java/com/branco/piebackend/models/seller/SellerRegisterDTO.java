package com.branco.piebackend.models.seller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerRegisterDTO {
    @NotBlank
    private String brand;

    @NotNull
    private Long ownerId;

    @NotNull
    private List<Long> universities;
}
