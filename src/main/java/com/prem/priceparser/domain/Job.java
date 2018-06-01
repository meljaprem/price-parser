package com.prem.priceparser.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prem.priceparser.domain.enums.ShopName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Job  {
    private Long productId;
    private ShopName shop;
    private String code;
}
