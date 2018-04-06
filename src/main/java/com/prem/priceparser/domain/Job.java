package com.prem.priceparser.domain;

import com.prem.priceparser.domain.enums.ShopName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    private Long product_id;
    private ShopName shop;
    private String code;
}
