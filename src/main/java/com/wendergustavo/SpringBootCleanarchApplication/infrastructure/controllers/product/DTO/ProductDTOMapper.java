package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.DTO;

import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.Product;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.controllers.CreateProductRequest;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.controllers.CreateProductResponse;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.controllers.UpdateProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOMapper {

    public CreateProductResponse toCreateProductResponse(Product product) {
        return new CreateProductResponse(
                product.name(),
                product.price()
        );
    }

    public Product toProduct(CreateProductRequest request) {
        return toProductInternal(request.name(), request.price(), null);
    }

    public Product toProduct(UpdateProductRequest request, Long id) {
        return toProductInternal(request.name(), request.price(), id);
    }

    public ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.id(),
                product.name(),
                product.price()
        );
    }

    private Product toProductInternal(String name, double price, Long id) {
        return new Product(id, name.trim(), Math.round(price * 100.0) / 100.0);
    }
}

