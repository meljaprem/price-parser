package com.prem.priceparser.rabbitmq.consumers;

import com.prem.priceparser.domain.JobResult;
import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.entity.ShopPrice;
import com.prem.priceparser.exceptions.ExceptionErrorCode;
import com.prem.priceparser.exceptions.GenericBusinessException;
import com.prem.priceparser.repository.ShopPriceRepository;
import com.prem.priceparser.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 02.06.2018
 */

@Component
@Slf4j
@PropertySource("classpath:rabbitmq.properties")
@RequiredArgsConstructor
public class OutboundConsumer {

    private final ProductService productService;
    private final ShopPriceRepository shopPriceRepository;

    @RabbitListener(queues = "${outbound.queue.results}")
    public void receiveInboundRozetka(JobResult result) {
        log.debug("Job results received: {}", result);
        Product product = productService.getProductById(result.getProduct_id())
                .orElseThrow(() -> new GenericBusinessException(ExceptionErrorCode.PRODUCT_NOT_FOUND));
        ShopPrice shopPrice = new ShopPrice();
        shopPrice.setProduct(product);
        shopPrice.setShop(result.getShop());
        shopPrice.setLastCheckedDate(result.getDate());
        shopPrice.setPrice(result.getPrice());

        Set<ShopPrice> shopsPrices = product.getShopsPrices();
        log.debug("ShopPrices before updating: {}", shopsPrices);
        shopsPrices.remove(shopPrice);
        log.debug("ShopPrices after removing: {}", shopsPrices);
        shopsPrices.add(shopPrice);
        log.debug("ShopPrices after updating: {}", shopsPrices);
        productService.updateProduct(product);
    }

}
