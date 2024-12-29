package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways;

import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.Product;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.impl.ProductRepositoryGateway;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.mapper.ProductEntityMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repositories.product.ProductEntity;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repositories.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryGatewayTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductEntityMapper productEntityMapper;

    @InjectMocks
    private ProductRepositoryGateway productRepositoryGateway;

    @Nested
    class CreateProduct {

        @Test
        @DisplayName("Should create a product with success")
        void shouldCreateAProductWithSuccess() {

            var input = new Product(
                    1L,
                    "Camisa",
                    35);

            var productEntity = new ProductEntity(
                    1L,
                    "Camisa",
                    35);

            when(productEntityMapper.toEntity(input)).thenReturn(productEntity);
            when(productRepository.save(productEntity)).thenReturn(productEntity);
            when(productEntityMapper.toDomainObj(productEntity)).thenReturn(input);

            var result = productRepositoryGateway.createProduct(input);

            verify(productEntityMapper, times(1)).toEntity(input);
            verify(productRepository, times(1)).save(productEntity);
            verify(productEntityMapper, times(1)).toDomainObj(productEntity);

            assertNotNull(result, "O resultado não deveria ser nulo");
            assertEquals(input.name(), result.name(), "O nome do produto deveria ser igual");
            assertEquals(input.price(), result.price(), "O preço do produto deveria ser igual");
        }

        @Test
        @DisplayName("Should throw exception when mapper fails during toEntity")
        void shouldThrowExceptionWhenMapperFails() {

            var input = new Product(
                    1L,
                    "Camisa",
                    35);

            when(productEntityMapper.toEntity(input)).thenThrow(new RuntimeException("Mapper error"));

            assertThrows(RuntimeException.class, () -> productRepositoryGateway.createProduct(input));
            verify(productEntityMapper, times(1)).toEntity(input);
            verifyNoInteractions(productRepository);
        }

        @Test
        @DisplayName("Should throw exception when repository fails during save")
        void shouldThrowExceptionWhenRepositoryFails() {

            var input = new Product(
                    1L,
                    "Camisa",
                    35);

            var productEntity = new ProductEntity(
                    1L,
                    "Camisa",
                    35);

            when(productEntityMapper.toEntity(input)).thenReturn(productEntity);
            when(productRepository.save(productEntity)).thenThrow(new RuntimeException("Database error"));

            assertThrows(RuntimeException.class, () -> productRepositoryGateway.createProduct(input));

            verify(productEntityMapper, times(1)).toEntity(input);
            verify(productRepository, times(1)).save(productEntity);

            verifyNoMoreInteractions(productEntityMapper, productRepository);
        }

        @Test
        @DisplayName("Should throw exception when repository returns null")
        void shouldThrowExceptionWhenRepositoryReturnsNull() {
            var input = new Product(1L, "Camisa", 35);
            var productEntity = new ProductEntity(1L, "Camisa", 35);

            when(productEntityMapper.toEntity(input)).thenReturn(productEntity);
            when(productRepository.save(productEntity)).thenReturn(null);

            assertThrows(IllegalStateException.class, () -> productRepositoryGateway.createProduct(input));

            verify(productEntityMapper, times(1)).toEntity(input);
            verify(productRepository, times(1)).save(productEntity);
        }


    }
}