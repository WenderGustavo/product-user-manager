package com.wendergustavo.SpringBootCleanarchApplication.application.interfaces;

import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {
    Product createProduct(Product product);
    List<Product> findAll();
   Optional<Product> findById(long id);
    void deleteProduct (long id);
    Optional<Product> updateProduct(long id, Product productUpdate);
}
