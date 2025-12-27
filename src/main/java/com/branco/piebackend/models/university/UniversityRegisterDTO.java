package com.branco.piebackend.models.university;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class UniversityRegisterDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String alias;

    @NotBlank
    private String emailDomain;

    @NotNull
    @Min(-90) @Max(90)
    private Double centerLatitude;

    @NotNull
    @Min(-180) @Max(180)
    private Double centerLongitude;
}
