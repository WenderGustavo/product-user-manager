    package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways;

    import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.Product;
    import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.product.ProductEntity;

    public class ProductEntityMapper {

        ProductEntity toEntity(Product productDomainObj) {
            return new ProductEntity(
                    productDomainObj.id(),
                    productDomainObj.name(),
                    productDomainObj.price());
        }
        public Product toDomainObj(ProductEntity productEntity){
            return new Product(
                    productEntity.getId(),
                    productEntity.getName(),
                    productEntity.getPrice());
        }

    }

