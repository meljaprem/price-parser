package com.prem.priceparser.domain;

import com.prem.priceparser.domain.enums.ShopName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobResult {
    private Long product_id;
    private ShopName shop;
    private Float price;
    private Boolean available;
    private String currency;
}
