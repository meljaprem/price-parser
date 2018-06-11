package com.prem.priceparser.helpers;

import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.enums.ShopName;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ProductUtilsTest {


    @Test
    public void parseJobsFromListOfProduct2() {
        Product product1 = mock(Product.class);
        Map<ShopName, String> map = new HashMap<>();
        map.put(ShopName.ROZETKA,"Code1");
        map.put(ShopName.CITRUS,"Code2");
        when(product1.getCodesMap()).thenReturn(map);
        when(product1.getId()).thenReturn(1L);

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            product1.setId((long) (i+1));
            products.add(product1);
        }

        long time1 = System.nanoTime();
        List<Job> jobs = ProductUtils.parseJobsFromListOfProduct(products);
        long time2 = System.nanoTime();

        log.info("Time for Pasha realisation: {}", time2-time1);
    }
}