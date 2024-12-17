package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.impl;

import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.ProductGateway;
import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.Product;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.mapper.ProductEntityMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repository.product.ProductEntity;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repository.product.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class ProductRepositoryGateway implements ProductGateway {

    private final ProductEntityMapper productEntityMapper;
    private final ProductRepository productRepository;

    public ProductRepositoryGateway(ProductEntityMapper productEntityMapper, ProductRepository productRepository) {
        this.productEntityMapper = productEntityMapper;
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product productDomainObj) {
        ProductEntity productEntity = productEntityMapper.toEntity(productDomainObj);
        ProductEntity savedEntity = productRepository.save(productEntity);
        return productEntityMapper.toDomainObj(savedEntity);
    }

    @Override
    public List<Product> findAll() {
        Iterable<ProductEntity> productEntities = productRepository.findAll();
        return StreamSupport.stream(productEntities.spliterator(), false)
                .map(productEntityMapper::toDomainObj)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id)
                .map(productEntityMapper::toDomainObj);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> updateProduct(long id, Product productUpdate) {
        Optional<ProductEntity> optionalEntity = productRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            return Optional.empty();
        }

        ProductEntity existingEntity = optionalEntity.get();
        existingEntity.setName(productUpdate.name());
        existingEntity.setPrice(productUpdate.price());

        ProductEntity updatedEntity = productRepository.save(existingEntity);
        return Optional.of(productEntityMapper.toDomainObj(updatedEntity));
    }
}
