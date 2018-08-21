package com.prem.priceparser.services.pricecheckers;

import com.prem.priceparser.services.pricecheckers.qualifiers.ComfyChecker;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 03.05.2018
 */

@ComfyChecker
@Service
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
        log.debug("Parsing document using cssQuery : {}", cssQuery);
        Element element = document.selectFirst(cssQuery);
        if(element != null){
            log.trace("Parsing element using attrKey: {}", attrKey);
            String strPrice = element.attr(attrKey);
            return Double.parseDouble(strPrice);
        }
        return null;

    }

}
