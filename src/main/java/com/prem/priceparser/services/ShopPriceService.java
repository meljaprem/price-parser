package com.prem.priceparser.services;

import com.prem.priceparser.domain.entity.ShopPrice;
import com.prem.priceparser.domain.enums.ShopName;
import com.prem.priceparser.repository.ShopPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 14.06.2018
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopPriceService {

    private final ShopPriceRepository shopPriceRepository;

    @Transactional
    public void deleteShopPriceByProductIdAndShop(Long productId, ShopName shopName) {
        log.trace("Deleting shopPrice with productId {} and shop {}", productId, shopName);
        long l = shopPriceRepository.deleteByProductIdAndShop(productId, shopName);
        log.trace("deleted {} items", l);
        log.trace("ShopPrice with productId {} and shop {} DELETED!", productId, shopName);
    }


    @Transactional
    public void deleteTheSameShopPrice(ShopPrice shopPrice) {
        deleteShopPriceByProductIdAndShop(shopPrice.getProduct().getId(), shopPrice.getShop());
    }
}
