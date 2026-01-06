package com.branco.piebackend.mappers;

import com.branco.piebackend.entities.OrderEntity;
import com.branco.piebackend.models.order.OrderResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDTO convertToResponseDTO(OrderEntity entity){
        return OrderResponseDTO.builder()
                .id(entity.getId())
                .total(entity.getTotal())
                .state(entity.getState())
                .sellerId(entity.getSeller().getId())
                .brand(entity.getSeller().getBrand())
                .build();
    }
}
