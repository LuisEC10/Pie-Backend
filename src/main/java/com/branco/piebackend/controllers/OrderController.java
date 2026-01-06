package com.branco.piebackend.controllers;

import com.branco.piebackend.models.order.OrderRegisterDTO;
import com.branco.piebackend.models.order.OrderResponseDTO;
import com.branco.piebackend.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'SELLER', 'ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(Authentication authentication){
        List<OrderResponseDTO> orders = this.orderService.getMyOrders(authentication.getName());
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'SELLER', 'ADMIN', 'DELIVERY')")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRegisterDTO orderRegisterDTO, BindingResult result,
                                         Authentication authentication){
        if(result.hasErrors()){
            validation(result);
        }
        try {
            List<OrderResponseDTO> newOrders = this.orderService.createOrder(authentication.getName(), orderRegisterDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newOrders);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private static ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "The field " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
