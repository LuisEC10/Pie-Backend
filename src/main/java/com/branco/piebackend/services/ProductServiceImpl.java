package com.branco.piebackend.services;

import com.branco.piebackend.entities.ProductEntity;
import com.branco.piebackend.entities.SellerEntity;
import com.branco.piebackend.mappers.ProductMapper;
import com.branco.piebackend.models.product.ProductRegisterDTO;
import com.branco.piebackend.models.product.ProductResponseDTO;
import com.branco.piebackend.models.product.ProductUpdateDTO;
import com.branco.piebackend.repositories.ProductRepository;
import com.branco.piebackend.repositories.SellerRepository;
import com.branco.piebackend.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
                              SellerRepository sellerRepository){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.sellerRepository = sellerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        return this.productRepository.findAll()
                .stream()
                .map(this.productMapper::convertToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        return this.productRepository.findAll(pageable)
                .map(this.productMapper::convertToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findSellerProducts(Long sellerId, Pageable pageable) {
        return this.productRepository.findAllBySeller_Id(sellerId, pageable)
                .map(this.productMapper::convertToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findByProductName(String name, Pageable pageable) {
        return this.productRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(this.productMapper::convertToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findByStock(Integer stock, Pageable pageable) {
        return this.productRepository.findByStockGreaterThan(stock, pageable)
                .map(this.productMapper::convertToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findBySellerAndStock(Long sellerId, Integer stock, Pageable pageable) {
        return this.productRepository.findBySeller_IdAndStockGreaterThan(sellerId, stock, pageable)
                .map(this.productMapper::convertToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductResponseDTO> findById(Long id) {
        return this.productRepository.findById(id)
                .map(this.productMapper::convertToResponseDTO);
    }

    @Override
    @Transactional
    public ProductResponseDTO save(ProductRegisterDTO product, String userCode) {
        SellerEntity seller = this.sellerRepository.findByOwner_Code(userCode).orElseThrow(
                () -> new RuntimeException("Not seller found with User Code: " + userCode)
        );
        ProductEntity entity = this.productMapper.convertToEntity(product);
        entity.setSeller(seller);
        ProductEntity saved = this.productRepository.save(entity);
        return this.productMapper.convertToResponseDTO(saved);
    }

    @Override
    @Transactional
    public Optional<ProductResponseDTO> update(ProductUpdateDTO product, Long id, String userCode) {
        Optional<ProductEntity> optionalProduct = this.productRepository.findById(id);
        if(optionalProduct.isPresent()){
            ProductEntity productDB = optionalProduct.get();
            validateProductOwnership(productDB, userCode);
            this.productMapper.updateProductFromDTO(product, productDB);
            return Optional.of(this.productMapper.convertToResponseDTO(this.productRepository.save(productDB)));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteById(Long id, String userCode) {
        Optional<ProductEntity> product = this.productRepository.findById(id);
        if(product.isPresent()){
            validateProductOwnership(product.get(), userCode);
            this.productRepository.deleteById(id);
        }
    }

    private void validateProductOwnership(ProductEntity product, String userCode) {
        String ownerUsername = product.getSeller().getOwner().getCode();
        if (!ownerUsername.equals(userCode)) {
            throw new AccessDeniedException("You are not the owner of this product");
        }
    }
}
