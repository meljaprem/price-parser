package com.prem.priceparser.domain.dto;


import com.prem.priceparser.domain.enums.ShopName;
import lombok.*;

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
