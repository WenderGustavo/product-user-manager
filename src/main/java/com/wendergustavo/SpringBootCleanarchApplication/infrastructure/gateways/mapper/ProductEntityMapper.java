    package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.gateways.mapper;

    import com.wendergustavo.SpringBootCleanarchApplication.domain.entity.Product;
    import com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repository.product.ProductEntity;

    public class ProductEntityMapper {

       public ProductEntity toEntity(Product productDomainObj) {
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

