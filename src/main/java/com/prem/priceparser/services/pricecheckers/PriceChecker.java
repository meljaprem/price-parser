package com.prem.priceparser.services.pricecheckers;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 23.04.2018
 */

@Getter
@Setter
@Slf4j
public abstract class PriceChecker {

    private Optional<Document> getDocument(String productId) {
        String urlToCheck = getShopAddress() + productId;
        log.debug("Getting document from url: {}", urlToCheck);
        Document doc = null;
        try {
            doc = Jsoup.connect(urlToCheck).followRedirects(true).get();
            log.debug("Document is not null: {}", doc != null);
            if (doc != null) {
                log.trace("Document which downloaded: {}", doc.body());
            }
        } catch (IOException e) {
            log.error("Exception while loading document from url: {}", urlToCheck, e);
        }

        return Optional.ofNullable(doc);
    }

    public Double getPrice(String productId) {
        log.debug("Start checking price");
        Document document = getDocument(productId)
                .orElseThrow(IllegalStateException::new);
        Double price = parseDocument(document);
        log.debug("Parsed price: {}, shopAddress: {}", price, getShopAddress());
        return price;
    }

    protected abstract String getShopAddress();

    protected abstract Double parseDocument(Document document);
}
