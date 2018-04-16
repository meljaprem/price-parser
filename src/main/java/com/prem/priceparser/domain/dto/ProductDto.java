package com.prem.priceparser.domain.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    private String name;
    private Double expectedPrice;
}
