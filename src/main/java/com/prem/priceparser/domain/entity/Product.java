package com.prem.priceparser.domain.entity;


import com.prem.priceparser.domain.enums.ShopName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,
                targetEntity = User.class)
    private Long user;

    @Column
    private Double price;

    @Column
    private String name;

    @Column
    private Double expectedPrice;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_codes")
    @MapKeyColumn(name = "shop_name")
    @MapKeyEnumerated(value = EnumType.STRING)
    @Column(name = "code")
    private Map<ShopName, String> codesMap;

    public Map<ShopName, String> getCodesMap() {
        if (codesMap == null) {
            codesMap = new HashMap<>();
        }
        return codesMap;
    }
}
