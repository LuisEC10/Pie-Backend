package com.branco.piebackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false) // not unique because other sellers can have the same product
    private String name;

    @NotNull
    @Column(nullable = false)
    private Double price;

    @NotNull
    @Column(nullable = false)
    private Integer stock;

    @NotBlank
    @Column(nullable = false, length = 1000)
    private String description;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String imageURL;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    @ToString.Exclude
    private SellerEntity seller;
}
