package com.prem.priceparser.helpers;

import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.entity.Product;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductUtils {

    public static List<Job> parseJobsFromProduct(Product product) {
        return product
                .getCodesMap()
                .entrySet()
                .stream()
                .map(entry -> new Job(product.getId(), entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static List<Job> parseJobsFromListOfProduct(List<Product> products) {
        return products
                .parallelStream()
                .map(product -> product
                        .getCodesMap()
                        .entrySet()
                        .stream()
                        .map(e -> new Job(product.getId(), e.getKey(), e.getValue()))
                )
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }
}
