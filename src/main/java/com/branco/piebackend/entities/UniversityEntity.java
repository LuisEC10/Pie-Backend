package com.branco.piebackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "universities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String alias;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String emailDomain;

    @NotNull
    @Column(nullable = false)
    private Double centerLatitude;

    @NotNull
    @Column(nullable = false)
    private Double centerLongitude;

}
