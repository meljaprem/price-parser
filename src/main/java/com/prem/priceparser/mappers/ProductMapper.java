package com.prem.priceparser.mappers;

import com.prem.priceparser.domain.dto.ProductDto;
import com.prem.priceparser.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements DtoMapper<Product, ProductDto> {


    @Override
    public ProductDto convertToDto(Product product) {
        return ProductDto.builder()
                .expectedPrice(product.getExpectedPrice())
                .id(product.getId())
                .name(product.getName())
                .build();
    }

    @Override
    public Product convertFromDto(ProductDto dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .expectedPrice(dto.getExpectedPrice())
                .build();
    }
}
