package com.prem.priceparser.helpers;

import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.enums.ShopName;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ProductUtilsTest {


    @Test
    @Ignore
    public void parseJobsFromListOfProduct2() {
        Product product1 = mock(Product.class);
        Map<ShopName, String> map = new HashMap<>();
        map.put(ShopName.ROZETKA, "Code1");
        when(product1.getCodesMap()).thenReturn(map);
        when(product1.getId()).thenReturn(1L);

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            product1.setId((long) (i + 1));
            products.add(product1);
        }

        long time1 = System.nanoTime();
        List<Job> jobs = ProductUtils.parseJobsFromListOfProduct(products);
        long time2 = System.nanoTime();

        log.info("Time for Pasha realisation: {}", time2 - time1);
    }



    @Test
    @Ignore
    public void rozetkaPriceCheckerTest2() throws IOException {

        String code = "p18843076";
        String cssQuery = "span.detail-price-uah meta[itemprop=\"price\"]";
        String attrKey = "content";
        Document doc = Jsoup.connect("https://rozetka.com.ua/offer/" + code).followRedirects(true).get();
        if (doc != null) {
//            log.trace("Document which downloaded:\n {}", doc.body());
            Element element = doc.selectFirst(cssQuery);
            if (element != null) {
                log.trace("Element which downloaded:\n {}", element.toString());
                String attr = element.attr(attrKey);
                log.debug("Price: " + attr);
            } else {
                log.error("Element is null!");
            }
        }
    }
}