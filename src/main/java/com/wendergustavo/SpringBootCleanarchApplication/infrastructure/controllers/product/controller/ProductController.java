package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.controller;

import com.wendergustavo.SpringBootCleanarchApplication.application.productcase.ProductInteractor;
import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.Product;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.DTO.ProductDTO;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.DTO.ProductDTOMapper;
import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.DTO.ProductListResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductInteractor productInteractor;
    private final ProductDTOMapper productDTOMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductInteractor createProductInteractor, ProductDTOMapper productDTOMapper) {
        this.productInteractor = createProductInteractor;
        this.productDTOMapper = productDTOMapper;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody @Valid CreateProductRequest request) {
        logger.info("Recebida solicitação para criar produto com nome '{}'", request.name());
        Product product = productDTOMapper.toProduct(request);
        Product createdProduct = productInteractor.createProduct(product);
        logger.info("Produto criado com sucesso. ID: {}", createdProduct.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTOMapper.toCreateProductResponse(createdProduct));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productInteractor.deleteProduct(id);
        logger.info("Produto com ID {} foi deletado com sucesso.", id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UpdateProductResponse> updateProduct(@PathVariable long id, @RequestBody @Valid UpdateProductRequest request) {
        Product productUpdate = productDTOMapper.toProduct(request, id);
        Product updateProduct = productInteractor.updateProduct(id,productUpdate);
        return ResponseEntity.ok(new UpdateProductResponse("Produto atualizado com sucesso", updateProduct.id()));
    }

    @GetMapping
    public ResponseEntity<ProductListResponse> listProducts() {
        logger.info("Recebida solicitação para listar produtos.");
        List<Product> products = productInteractor.findAll();
        List<ProductDTO> productDTOs = products.stream()
                .map(productDTOMapper::toDTO)
                .collect(Collectors.toList());
        logger.info("Encontrados {} produtos.", productDTOs.size());
        return ResponseEntity.ok(new ProductListResponse(productDTOs, productDTOs.size()));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable long id) {
        logger.info("Recebida solicitação para buscar produto com ID {}", id);
        Product product = productInteractor.findById(id);
        ProductDTO productDTO = productDTOMapper.toDTO(product);
        logger.info("Produto encontrado. ID: {}, Nome: {}", product.id(), product.name());
        return ResponseEntity.ok(productDTO);
    }


}
