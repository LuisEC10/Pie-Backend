package com.branco.piebackend.models.seller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Select a university")
    private List<Long> universities;
}
