package com.branco.piebackend.services;

import com.branco.piebackend.entities.CartEntity;
import com.branco.piebackend.entities.CartItemEntity;
import com.branco.piebackend.entities.ProductEntity;
import com.branco.piebackend.entities.UserEntity;
import com.branco.piebackend.mappers.CartItemMapper;
import com.branco.piebackend.mappers.CartMapper;
import com.branco.piebackend.models.cart.CartItemRequestDTO;
import com.branco.piebackend.models.cart.CartItemResponseDTO;
import com.branco.piebackend.models.cart.CartResponseDTO;
import com.branco.piebackend.repositories.CartItemRepository;
import com.branco.piebackend.repositories.CartRepository;
import com.branco.piebackend.repositories.ProductRepository;
import com.branco.piebackend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper, UserRepository userRepository,
                           ProductRepository productRepository){
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Optional<CartResponseDTO> addItemToCart(Long userId, CartItemRequestDTO request) {
        CartEntity cart = this.cartRepository.findByUserId(userId).orElseGet(() -> {
            UserEntity user = this.userRepository.findById(userId)
                .orElseThrow();
            return CartEntity.builder().user(user).total(0.0).build();
        });
        ProductEntity product = this.productRepository.findById(request.getProductId()).orElseThrow();
        Optional<CartItemEntity> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();
        if(existingItem.isPresent()){
            CartItemEntity item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            item.setTotal(item.getQuantity() * product.getPrice());
        }else {
            CartItemEntity newItem = CartItemEntity.builder()
                    .quantity(request.getQuantity())
                    .total(request.getQuantity() * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(newItem);
        }
        double newTotal = cart.getItems().stream()
                .mapToDouble(CartItemEntity::getTotal).sum();
        cart.setTotal(newTotal);
        CartEntity savedCart = this.cartRepository.save(cart);
        return Optional.of(this.cartMapper.convertToResponseDTO(savedCart));
    }

    @Override
    @Transactional
    public Optional<CartResponseDTO> removeItemFromCart(Long userId, Long cartItemId) {
        CartEntity cart = this.cartRepository.findByUserId(userId).orElseThrow();
        Optional<CartItemEntity> item = cart.getItems().stream()
                .filter(it -> it.getId().equals(cartItemId)).findFirst();
        cart.getItems().remove(item.orElseThrow());
        double newTotal = cart.getItems().stream()
                .mapToDouble(CartItemEntity::getTotal).sum();
        cart.setTotal(newTotal);
        CartEntity savedCart = this.cartRepository.save(cart);
        return Optional.of(this.cartMapper.convertToResponseDTO(savedCart));
    }

    @Override
    @Transactional
    public Optional<CartResponseDTO> getCartByUser(Long userId) {
        Optional<CartEntity> optionalCart = this.cartRepository.findByUserId(userId);
        if(optionalCart.isPresent()){
            return Optional.of(this.cartMapper.convertToResponseDTO(optionalCart.get()));
        }
        UserEntity user = this.userRepository.findById(userId).orElseThrow();
        CartEntity newCart = CartEntity.builder()
                .total(0.0)
                .user(user).build();
        CartEntity saved = this.cartRepository.save(newCart);
        return Optional.of(this.cartMapper.convertToResponseDTO(saved));
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        CartEntity cart = this.cartRepository.findByUserId(userId).orElseThrow();
        cart.getItems().clear();
        cart.setTotal(0.0);
        this.cartRepository.save(cart);
    }
}
