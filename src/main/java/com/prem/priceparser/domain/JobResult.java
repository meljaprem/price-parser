package com.prem.priceparser.domain;

import com.prem.priceparser.domain.enums.ShopName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResult {
    private Long product_id;
    private ShopName shop;
    private Double price;
    private Boolean available = true;
    private Date date;
}
