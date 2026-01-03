package com.branco.piebackend.repositories;

import com.branco.piebackend.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

}
