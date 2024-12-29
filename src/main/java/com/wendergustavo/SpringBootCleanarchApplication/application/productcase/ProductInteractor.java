package com.wendergustavo.SpringBootCleanarchApplication.application.productcase;

import com.wendergustavo.SpringBootCleanarchApplication.exceptions.ProductNotFoundException;
import com.wendergustavo.SpringBootCleanarchApplication.application.interfaces.ProductGateway;
import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInteractor {

    private final ProductGateway productGateway;
    private static final Logger logger = LoggerFactory.getLogger(ProductInteractor.class);

    public ProductInteractor(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public Product createProduct(Product product) {

        Product createdProduct = productGateway.createProduct(product);

        logger.info("Produto criado com sucesso. ID: {}, Nome: {}", createdProduct.id(), createdProduct.name());

        return createdProduct;
    }


    public List<Product> findAll() {
        List<Product> products = productGateway.findAll();
        logger.info("Encontrados {} produtos no sistema.", products.size());
        return products;
    }

    public void deleteProduct(long id) {
        try {
            Product product = findByIdOrThrow(id);

            productGateway.deleteProduct(id);

            boolean exists = productGateway.findById(id).isPresent();

            if (exists) {
                logger.error("Falha ao deletar o produto. Produto ainda existe no sistema. ID: {}", id);
                throw new RuntimeException("Falha ao deletar o produto. Produto ainda existe no sistema.");
            }

            logger.info("Produto deletado com sucesso. ID: {}, Nome: {}", product.id(), product.name());
        } catch (ProductNotFoundException e) {
            logger.error("Produto não encontrado. ID: {}", id);
            throw new RuntimeException("Produto não encontrado para exclusão.", e);
        } catch (Exception e) {
            logger.error("Erro ao tentar deletar o produto com ID: {}", id, e);
            throw new RuntimeException("Erro ao tentar deletar o produto. Verifique os dados e tente novamente.", e);
        }
    }

    public Product updateProduct(long id, Product productUpdate) {
        Product existingProduct = findById(id);

        Product updatedProduct = existingProduct.withName(
                productUpdate.name() != null && !productUpdate.name().isBlank() ? productUpdate.name() : existingProduct.name()
        ).withPrice(
                productUpdate.price() > 0 ? productUpdate.price() : existingProduct.price()
        );

        return productGateway.updateProduct(id, updatedProduct)
                .orElseThrow(() -> new ProductNotFoundException("Failed to update the product with ID " + id));
    }

    public Product findById(long id) {
        return findByIdOrThrow(id);
    }

    private Product findByIdOrThrow(long id) {
        return productGateway.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID " + id));
    }

}

