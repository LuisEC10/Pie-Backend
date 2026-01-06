package com.branco.piebackend.models.order;

import com.branco.piebackend.entities.states.ItemState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private Double total;
    private ItemState state;

    private Double shipLatitude;
    private Double shipLongitude;

    private Long sellerId;
    private String brand;

    private List<OrderItemResponseDTO> items;
}
