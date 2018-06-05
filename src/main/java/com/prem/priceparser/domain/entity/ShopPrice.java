package com.prem.priceparser.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prem.priceparser.domain.enums.ShopName;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 02.06.2018
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shops_prices",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "shop"}))
public class ShopPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShopName shop;
    @Column(nullable = false)
    private Date lastCheckedDate;
    @Column(nullable = true)
    private Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopPrice shopPrice = (ShopPrice) o;

        if (!product.equals(shopPrice.product)) return false;
        return shop == shopPrice.shop;
    }

    @Override
    public int hashCode() {
        int result = product.hashCode();
        result = 31 * result + shop.hashCode();
        return result;
    }



    @Override
    public String toString() {
        return "ShopPrice{" +
                "id=" + id +
                ", product=" + product.getId() +
                ", shop=" + shop +
                ", lastCheckedDate=" + lastCheckedDate +
                ", price=" + price +
                '}';
    }
}
