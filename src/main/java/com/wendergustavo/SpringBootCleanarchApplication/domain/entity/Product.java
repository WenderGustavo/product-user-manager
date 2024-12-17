package com.wendergustavo.SpringBootCleanarchApplication.domain.entity;

import com.wendergustavo.SpringBootCleanarchApplication.exceptions.InvalidArgumentDomainException;

public record Product(Long id, String name, double price) {

    public static final String PRODUCT_INVALIDO = "Campo 'Produto' não pode ser nulo ou vazio.";
    public static final String PRECO_INVALIDO = "Campo 'Preco' não pode ser menor ou igual a zero.";

    public Product {
        if (name == null || name.isBlank()) {
            throw new InvalidArgumentDomainException(PRODUCT_INVALIDO);
        }
        if (price <= 0) {
            throw new InvalidArgumentDomainException(PRECO_INVALIDO);
        }
    }

    public Product withName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new InvalidArgumentDomainException(PRODUCT_INVALIDO);
        }
        return new Product(this.id, newName, this.price);
    }

    public Product withPrice(double newPrice) {
        if (newPrice <= 0) {
            throw new InvalidArgumentDomainException(PRECO_INVALIDO);
        }
        return new Product(this.id, this.name, newPrice);
    }
}
