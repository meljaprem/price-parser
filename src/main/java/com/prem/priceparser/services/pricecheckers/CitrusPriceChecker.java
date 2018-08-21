package com.prem.priceparser.services.pricecheckers;

import com.prem.priceparser.services.pricecheckers.qualifiers.CitrusChecker;
import com.prem.priceparser.services.pricecheckers.qualifiers.RozetkaChecker;
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

@CitrusChecker
@Service
@Getter
@Setter
@Slf4j
@PropertySource("classpath:checkers.properties")
public class CitrusPriceChecker extends PriceChecker {

    private final static String SHOP_WEB_ADDRESS = "https://www.citrus.ua/";
    @Value("${pattern.citrus.cssQuery}")
    private String cssQuery;

    public CitrusPriceChecker() {
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
        if(element != null){
            log.trace("Element with price: {}", element.toString());
            String strPrice = element.text();
            return Double.parseDouble(strPrice.replaceAll(" ", ""));
        }
        return null;
    }

}
