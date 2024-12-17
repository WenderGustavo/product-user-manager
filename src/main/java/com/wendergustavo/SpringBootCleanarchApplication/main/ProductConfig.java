package com.wendergustavo.SpringBootCleanarchApplication.main;

import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.ProductGateway;
import com.wendergustavo.SpringBootCleanarchApplication.application.usecases.ProductInteractor;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.DTO.ProductDTOMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.ProductEntityMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.ProductRepositoryGateway;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.product.ProductRepository;
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
