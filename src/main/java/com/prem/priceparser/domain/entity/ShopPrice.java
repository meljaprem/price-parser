package com.prem.priceparser.domain.entity;

import com.prem.priceparser.domain.enums.ShopName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 02.06.2018
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shops_prices")
public class ShopPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShopName shop;
    @Column(nullable = false)
    private Date lastCheckedDate;

    public static ShopPrice.ShopPriceComparator comparator() {
        return new ShopPrice.ShopPriceComparator();
    }

    public static class ShopPriceComparator implements Comparator<ShopPrice> {
        @Override
        public int compare(ShopPrice o1, ShopPrice o2) {
            int i = o1.getProduct().getId()
                    .compareTo(o2.getProduct().getId());
            return i != 0 ? i : o1.getShop().compareTo(o2.getShop());
        }
    }
}
