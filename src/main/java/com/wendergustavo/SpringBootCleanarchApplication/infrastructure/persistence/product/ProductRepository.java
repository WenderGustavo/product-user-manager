package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.product;

import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository  extends JpaRepository<ProductEntity,Long> {
    Optional<ProductEntity> findById(Long id);
}
