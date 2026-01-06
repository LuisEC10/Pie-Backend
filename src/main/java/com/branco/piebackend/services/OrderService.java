package com.branco.piebackend.services;

import com.branco.piebackend.models.order.OrderRegisterDTO;
import com.branco.piebackend.models.order.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> createOrder(String userCode, OrderRegisterDTO orderRegisterDTO);

    List<OrderResponseDTO> getMyOrders(String userCode);
}
