package com.prem.priceparser.repository;

import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.entity.ShopPrice;
import com.prem.priceparser.domain.enums.ShopName;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ShopPriceRepository extends CrudRepository<ShopPrice, Long> {
    ShopPrice findByProductIdAndShop(Long productId, ShopName shop);

    ShopPrice findByProductAndShop(Product product, ShopName shop);

    Set<ShopPrice> findAllByProduct(Product product);

    Set<ShopPrice> findAllByProductId(Long productId);

    long deleteByProductIdAndShop(Long productId, ShopName shopName);
}
