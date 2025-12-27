package com.branco.piebackend.services;

import com.branco.piebackend.entities.ProductEntity;
import com.branco.piebackend.mappers.ProductMapper;
import com.branco.piebackend.models.product.ProductRegisterDTO;
import com.branco.piebackend.models.product.ProductResponseDTO;
import com.branco.piebackend.models.product.ProductUpdateDTO;
import com.branco.piebackend.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;

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
    public ProductResponseDTO save(ProductRegisterDTO product) {
        ProductEntity entity = this.productMapper.convertToEntity(product);
        ProductEntity saved = this.productRepository.save(entity);
        return this.productMapper.convertToResponseDTO(saved);
    }

    @Override
    @Transactional
    public Optional<ProductResponseDTO> update(ProductUpdateDTO product, Long id) {
        Optional<ProductEntity> optionalProduct = this.productRepository.findById(id);
        if(optionalProduct.isPresent()){
            ProductEntity productDB = optionalProduct.get();
            this.productMapper.updateProductFromDTO(product, productDB);
            return Optional.of(this.productMapper.convertToResponseDTO(this.productRepository.save(productDB)));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }
}
