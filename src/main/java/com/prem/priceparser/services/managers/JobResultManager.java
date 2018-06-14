package com.prem.priceparser.services.managers;

import com.prem.priceparser.domain.JobResult;
import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.entity.ShopPrice;
import com.prem.priceparser.services.ProductService;
import com.prem.priceparser.services.ShopPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 02.06.2018
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class JobResultManager {
    private final ProductService productService;
    private final ShopPriceService shopPriceService;

    @Transactional
    public void parseResult(JobResult result){
        log.debug("Managing result: {}", result);
        Product product = productService.getProductById(result.getProduct_id());
        ShopPrice shopPrice = createShopPrice(result, product);
        Set<ShopPrice> shopsPrices = product.getShopsPrices();
        shopsPrices.remove(shopPrice);
        shopsPrices.add(shopPrice);
        shopPriceService.deleteShopPriceByProductIdAndShop(shopPrice.getProduct().getId(), shopPrice.getShop());
        productService.updateProduct(product);
        log.debug("Managing result for {} done!", result);
    }

    private ShopPrice createShopPrice(JobResult result, Product product) {
        ShopPrice shopPrice = new ShopPrice();
        shopPrice.setProduct(product);
        shopPrice.setShop(result.getShop());
        shopPrice.setLastCheckedDate(result.getDate());
        shopPrice.setPrice(result.getPrice());
        return shopPrice;
    }
}
