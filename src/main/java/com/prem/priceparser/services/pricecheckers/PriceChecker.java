package com.prem.priceparser.services.pricecheckers;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 23.04.2018
 */

@Getter
@Setter
@Slf4j
public abstract class PriceChecker {

    private Document getDocument(String productId) throws IOException {
        String urlToCheck = getShopAddress() + productId;
        log.debug("Getting document from url: {}", urlToCheck);
        Document doc = Jsoup.connect(urlToCheck).followRedirects(true).get();
        log.trace("Document which downloaded: {}", doc.body());
        return doc;
    }

    public Double getPrice(String productId) throws IOException {
        if (productId == null) throw new IllegalArgumentException("productId is null");
        Document document = getDocument(productId);
        return parseDocument(document);
    }

    public abstract String getShopAddress();

    protected abstract Double parseDocument(Document document);
}
