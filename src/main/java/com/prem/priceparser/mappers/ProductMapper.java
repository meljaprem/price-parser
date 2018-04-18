package com.prem.priceparser.mappers;

import com.prem.priceparser.domain.dto.ProductDto;
import com.prem.priceparser.domain.entity.Product;

public class ProductMapper implements DtoMapper<Product, ProductDto> {


    @Override
    public ProductDto convertToDto(Product object) {
        return null;
    }

    @Override
    public Product convertFromDto(ProductDto dto) {
        return null;
    }
}
