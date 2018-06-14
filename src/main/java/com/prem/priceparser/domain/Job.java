package com.prem.priceparser.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prem.priceparser.domain.enums.ShopName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("shop")
public class Job  {
    private Long productId;
    private ShopName shop;
    private String code;
}
