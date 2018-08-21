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
    public void rozetkaPriceCheckerTest2() throws IOException {

        String code = "smartfony/p20-black-huawei-626477.html";
        String cssQuery = "span.buy-general span.price-number";
        String attrKey = "content";
        Document doc = Jsoup.connect("https://www.citrus.ua/" + code)
                .followRedirects(true)
//                .header("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Mobile Safari/537.36")
                .get();
        if (doc != null) {
            log.trace("Document which downloaded:\n {}", doc.body());
            Element element = doc.selectFirst(cssQuery);
            if (element != null) {
                log.trace("Element which downloaded:\n {}", element.toString());
                String attr = element.text();
//                String attr = element.attr(attrKey);
                log.debug("Price: " + attr.replaceAll(" ", ""));
            } else {
                log.error("Element is null!");
            }
        }
    }
}