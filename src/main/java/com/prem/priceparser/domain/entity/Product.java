package com.prem.priceparser.domain.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prem.priceparser.domain.enums.ShopName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,
                targetEntity = User.class,
                optional = false)
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true) // otherwise first ref as POJO, others as id
    private User user;

    @Column(nullable = false)
    private String name;

    @Column
    private Double expectedPrice;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_codes")
    @MapKeyColumn(name = "shop_name")
    @MapKeyEnumerated(value = EnumType.STRING)
    @Column(name = "code")
    private Map<ShopName, String> codesMap;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "shops_prices")
    @MapKeyColumn(name = "shop_name")
    @MapKeyEnumerated(value = EnumType.STRING)
    @Column(name = "price")
    private Map<ShopName, Double> shopsPrices;


    public Map<ShopName, String> getCodesMap() {
        if (codesMap == null) {
            codesMap = new HashMap<>();
        }
        return codesMap;
    }

    public Map<ShopName, Double> getShopsPrices() {
        if (shopsPrices == null) {
            shopsPrices = new HashMap<>();
        }
        return shopsPrices;
    }

    public Product(User user, String name, Double expectedPrice) {
        this.user = user;
        this.name = name;
        this.expectedPrice = expectedPrice;
    }
}
