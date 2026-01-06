package com.branco.piebackend.services;

import com.branco.piebackend.entities.*;
import com.branco.piebackend.entities.states.ItemState;
import com.branco.piebackend.mappers.OrderMapper;
import com.branco.piebackend.models.order.OrderRegisterDTO;
import com.branco.piebackend.models.order.OrderResponseDTO;
import com.branco.piebackend.repositories.CartRepository;
import com.branco.piebackend.repositories.OrderRepository;
import com.branco.piebackend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository, UserRepository userRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public List<OrderResponseDTO> createOrder(String userCode, OrderRegisterDTO orderRegisterDTO) {
        UserEntity user = this.userRepository.findUserByCode(userCode)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        CartEntity cart = this.cartRepository.findByUserId(user.getId())
                .orElseThrow(
                        () -> new RuntimeException("Cart not found")
                );

        if(cart.getItems().isEmpty()){
            throw new RuntimeException("Cart is empty");
        }

        Map<SellerEntity, List<CartItemEntity>> itemsBySeller = cart.getItems().stream()
                .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct().getSeller()));

        List<OrderEntity> savedOrders = new ArrayList<>();

        itemsBySeller.forEach((seller, items) -> {
            OrderEntity order = OrderEntity.builder()
                    .user(user)
                    .seller(seller)
                    .state(ItemState.PENDING)
                    .shipLatitude(orderRegisterDTO.getShipLatitude())
                    .shipLongitude(orderRegisterDTO.getShipLongitude())
                    .total(0.0)
                    .build();

            List<OrderItemEntity> orderItems = new ArrayList<>();
            double orderTotal = 0.0;
            for (CartItemEntity cartItem : items) {
                Double currentPrice = cartItem.getProduct().getPrice();
                Double itemTotal = currentPrice * cartItem.getQuantity();

                OrderItemEntity orderItem = OrderItemEntity.builder()
                        .product(cartItem.getProduct())
                        .quantity(cartItem.getQuantity())
                        .priceAtPurchase(currentPrice)
                        .total(itemTotal)
                        .order(order)
                        .build();
                orderItems.add(orderItem);
                orderTotal += itemTotal;
            }
            order.setOrderItems(orderItems);
            order.setTotal(orderTotal);
            savedOrders.add(this.orderRepository.save(order));
        });

        cart.getItems().clear();
        cart.setTotal(0.0);
        this.cartRepository.save(cart);

        return savedOrders.stream()
                .map(this.orderMapper::convertToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public List<OrderResponseDTO> getMyOrders(String userCode) {
        UserEntity user = this.userRepository.findUserByCode(userCode).orElseThrow();
        return this.orderRepository.findByUser_Id(user.getId()).stream()
                .map(this.orderMapper::convertToResponseDTO)
                .toList();
    }
}
