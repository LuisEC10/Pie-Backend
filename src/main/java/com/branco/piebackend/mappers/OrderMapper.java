package com.branco.piebackend.mappers;

import com.branco.piebackend.entities.OrderEntity;
import com.branco.piebackend.entities.OrderItemEntity;
import com.branco.piebackend.models.order.OrderItemResponseDTO;
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
                .shipLatitude(entity.getShipLatitude())
                .shipLongitude(entity.getShipLongitude())
                .items(entity.getOrderItems().stream()
                        .map(this::convertItemToDTO)
                        .toList())
                .build();
    }

    private OrderItemResponseDTO convertItemToDTO(OrderItemEntity itemEntity) {
        return OrderItemResponseDTO.builder()
                .id(itemEntity.getId())
                .productId(itemEntity.getProduct().getId())
                .productName(itemEntity.getProduct().getName())
                .productImageURL(itemEntity.getProduct().getImageURL())
                .quantity(itemEntity.getQuantity())
                .priceAtPurchase(itemEntity.getPriceAtPurchase())
                .subtotal(itemEntity.getTotal())
                .build();
    }
}
