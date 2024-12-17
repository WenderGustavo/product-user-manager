package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product;

import com.wendergustavo.SpringBootCleanarchApplication.application.usecases.ProductInteractor;
import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.Product;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.DTO.ProductDTOMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.controllers.CreateProductRequest;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.controllers.CreateProductResponse;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.controllers.ProductController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductInteractor productInteract;

    @Mock
    private ProductDTOMapper productDTOMapper;

    @InjectMocks
    private ProductController productController;

    @Nested
    class CreateProduct {

        @Test
        @DisplayName("Should return 201 Created when product is successfully created")
        void shouldReturnCreatedWhenProductIsSuccessfullyCreated() {
            var request = new CreateProductRequest("Camisa", 35.0);
            var product = new Product(null, "Camisa", 35.0); // Nome e preço válidos
            var createdProduct = new Product(1L, "Camisa", 35.0);
            var response = new CreateProductResponse("Camisa", 35.0);

            when(productDTOMapper.toProduct(request)).thenReturn(product);
            when(productInteract.createProduct(product)).thenReturn(createdProduct);
            when(productDTOMapper.toCreateProductResponse(createdProduct)).thenReturn(response);

            ResponseEntity<CreateProductResponse> result = productController.createProduct(request);

            verify(productDTOMapper, times(1)).toProduct(request);
            verify(productInteract, times(1)).createProduct(product);
            verify(productDTOMapper, times(1)).toCreateProductResponse(createdProduct);

            assertNotNull(result, "The response should not be null");
            assertEquals(HttpStatus.CREATED, result.getStatusCode(), "The HTTP status should be 201 CREATED");
            assertEquals(response, result.getBody(), "The response body should contain the created product");
        }


        @Test
        @DisplayName("Should throw IllegalArgumentException when input is invalid")
        void shouldThrowBadRequestWhenInputIsInvalid() {
            var invalidRequest = new CreateProductRequest("", 0);
            when(productInteract.createProduct(any())).thenThrow(new IllegalArgumentException("Invalid produc input"));
            assertThrows(IllegalArgumentException.class, () -> productController.createProduct(invalidRequest));
            verify(productDTOMapper).toProduct(invalidRequest);
            verify(productInteract).createProduct(any());

        }

        @Test
        @DisplayName("Should return 500 Internal Server Error when use case throws exception")
        void shouldReturnInternalServerErrorWhenUseCaseThrowsException() {
            var request = new CreateProductRequest("Camisa", 35);
            var product = new Product(null, "Camisa", 35);

            when(productDTOMapper.toProduct(request)).thenReturn(product);
            when(productInteract.createProduct(product)).thenThrow(new RuntimeException("Unexpected error"));

            assertThrows(RuntimeException.class, () -> productController.createProduct(request));

            verify(productDTOMapper, times(1)).toProduct(request);
            verify(productInteract, times(1)).createProduct(product);
        }

        @Test
        @DisplayName("Should return 400 Bad Request for invalid input")
        void shouldReturnBadRequestForInvalidInput() {
            var invalidRequest = new CreateProductRequest("", 0);

            when(productInteract.createProduct(any())).
                    thenThrow(new IllegalArgumentException("Product name cannot be null or empty."));

            var exception = assertThrows(IllegalArgumentException.class,
                    () -> productController.createProduct(invalidRequest));

            assertEquals("Product name cannot be null or empty.", exception.getMessage(),
                    "The error message should match the validation error");

                verify(productInteract).createProduct(any());
                verify(productDTOMapper).toProduct(invalidRequest);
        }

    }
}
