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
@NoArgsConstructor
@AllArgsConstructor
public class SellerUpdateDTO {
    @NotBlank
    private String brand;

    @NotNull
    private List<Long> deliveries;

    @NotNull
    private List<Long> universities;
}
