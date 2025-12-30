package com.branco.piebackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String brand;

    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false, unique = true)
    private UserEntity owner;

    // Relation M - M -> deliveries can work for any seller
    // Use to get all deliveries working for a seller
    @ManyToMany
    @JoinTable(
            name = "seller_deliveries",
            joinColumns = @JoinColumn(name = "seller_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"seller_id", "driver_id"})}
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<UserEntity> deliveries = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "seller_university",
            joinColumns = @JoinColumn(name = "seller_id"),
            inverseJoinColumns = @JoinColumn(name = "university_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"seller_id", "university_id"})}
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<UniversityEntity> universities = new ArrayList<>();

    // Relation 1 - m -> 1 Seller has too many products
    // Use to get all products of a seller
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<ProductEntity> products = new ArrayList<>();
}
