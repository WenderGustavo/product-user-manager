package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.controllers.product.DTO;

import java.util.List;

public record ProductListResponse (List<ProductDTO> products, int total){
}
