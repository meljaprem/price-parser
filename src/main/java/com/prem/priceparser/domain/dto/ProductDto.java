package com.prem.priceparser.domain.dto;


import com.prem.priceparser.domain.enums.ShopName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ProductDto {

    private String name;
    private Double expectedPrice;
    private Map<ShopName, Double> shopPrices;
}
