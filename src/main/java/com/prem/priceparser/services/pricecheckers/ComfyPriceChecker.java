package com.prem.priceparser.services.pricecheckers;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 03.05.2018
 */

@Service("comfyPriceChecker")
@Getter
@Setter
@Slf4j
@PropertySource("classpath:checkers.properties")
public class ComfyPriceChecker extends PriceChecker {

    private final static String SHOP_WEB_ADDRESS = "https://comfy.ua/";
    @Value("${pattern.comfy.cssQuery}")
    private String cssQuery;
    @Value("${pattern.comfy.attrKey}")
    private String attrKey;

    public ComfyPriceChecker() {
    }

    @Override
    public String getShopAddress() {
        return SHOP_WEB_ADDRESS;
    }

    @Override
    protected Double parseDocument(Document document) {
        log.debug("Parsing document using cssQuery : {}, attrKey {}", cssQuery, attrKey);
        return Double.parseDouble(document
                .selectFirst(cssQuery)
                .attr(attrKey));
    }

}
