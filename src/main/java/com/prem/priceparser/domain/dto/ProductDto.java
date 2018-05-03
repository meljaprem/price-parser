package com.prem.priceparser.domain.dto;


import com.prem.priceparser.domain.enums.ShopName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDto {

    private Long id;
    private String name;
    private Double expectedPrice;
    private Map<ShopName, Double> shopPrices;
}
