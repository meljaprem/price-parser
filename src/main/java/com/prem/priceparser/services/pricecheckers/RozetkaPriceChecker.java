package com.prem.priceparser.services.pricecheckers;

import com.prem.priceparser.services.pricecheckers.qualifiers.RozetkaChecker;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 03.05.2018
 */

@RozetkaChecker
@Service
@Getter
@Setter
@Slf4j
@PropertySource("classpath:checkers.properties")
public class RozetkaPriceChecker extends PriceChecker {

    private final static String SHOP_WEB_ADDRESS = "https://rozetka.com.ua/offer/";
    @Value("${pattern.rozetka.cssQuery}")
    private String cssQuery;
    @Value("${pattern.rozetka.cssQuerySales}")
    private String cssQuerySales;
    @Value("${pattern.rozetka.attrKey}")
    private String attrKey;

    public RozetkaPriceChecker() {
    }

    @Override
    public String getShopAddress() {
        return SHOP_WEB_ADDRESS;
    }

    @Override
    protected Double parseDocument(Document document) {
        Element element;
        log.debug("Parsing document using cssQuery : {}", cssQuery);
        element = document.selectFirst(cssQuery);
        if (element == null) {
            log.debug("Element is null, trying to parse document using cssQuery : {}", cssQuerySales);
            element = document.selectFirst(cssQuerySales);
        }
        if(element != null){
            log.trace("Element with price: {}", element.toString());
            log.debug("Parsing element using attrKey: {}", attrKey);
            String strPrice = element.attr(attrKey);
            return Double.parseDouble(strPrice);
        }

        return null;
    }

}
