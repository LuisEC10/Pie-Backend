package com.branco.piebackend.entities;

import com.branco.piebackend.entities.states.ItemState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Column(nullable = false)
    private Double total;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemState state;

    @NotNull
    @Column(nullable = false)
    private Double shipLatitude;

    @NotNull
    @Column(nullable = false)
    private Double shipLongitude;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude // avoid infinite loops
    private ProductEntity product;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity userDelivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    @ToString.Exclude
    private ShoppingCartEntity shoppingCart;
}
