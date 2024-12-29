package com.wendergustavo.SpringBootCleanarchApplication.config;

import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.ProductGateway;
import com.wendergustavo.SpringBootCleanarchApplication.application.productcase.ProductInteractor;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.DTO.ProductDTOMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.mapper.ProductEntityMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.impl.ProductRepositoryGateway;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repositories.product.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    @Bean
    ProductInteractor createProductInteractor(ProductGateway productGateway) {
        return new ProductInteractor(productGateway);
    }

    @Bean
    ProductGateway productGateway(ProductRepository productRepository, ProductEntityMapper productEntityMapper) {
        return new ProductRepositoryGateway (productEntityMapper,productRepository);
    }

    @Bean
    ProductEntityMapper productEntityMapper() {
        return new ProductEntityMapper();
    }

    @Bean
    ProductDTOMapper productDTOMapper() {
        return new ProductDTOMapper();
    }
}
