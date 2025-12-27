package com.branco.piebackend.models.university;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UniversityResponseDTO {
    private Long id;
    private String name;
    private String alias;
    private String emailDomain;
    private Double centerLatitude;
    private Double centerLongitude;
}
