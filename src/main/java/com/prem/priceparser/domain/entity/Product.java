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
import java.util.Set;
import java.util.TreeSet;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ShopPrice> shopsPrices;


    public Map<ShopName, String> getCodes() {
        if (codesMap == null) {
            codesMap = new HashMap<>();
        }
        return codesMap;
    }

    public Set<ShopPrice> getShopsPrices() {
        if (shopsPrices==null){
            shopsPrices = new TreeSet<>(ShopPrice.comparator());
        }
        return shopsPrices;
    }

    public Product(User user, String name, Double expectedPrice) {
        this.user = user;
        this.name = name;
        this.expectedPrice = expectedPrice;
    }
}
