package com.prem.priceparser.helpers;

import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductUtils {

    public static List<Job> parseJobsFromProduct(Product product) {
        List<Job> jobs = new ArrayList<>();
        product.getCodesMap()
                .forEach((key, value) -> jobs.add(new Job(product.getId(), key, value)));
        return jobs;
    }

    public static List<Job> parseJobsFromListOfProduct2(List<Product> products) {
//        List<Job> jobs = new ArrayList<>();
//        products.parallelStream()
//                .map(Product::getCodesMap)
//                .forEach((key, value) -> jobs.add(new Job(product.getId(), key, value)));

        return products.parallelStream()
                .map(p -> p.getCodesMap()
                        .entrySet().stream()
                        .map(e -> new Job(p.getId(), e.getKey(), e.getValue()))
                        .collect(Collectors.toList())
                )
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }


    public static List<Job> parseJobsFromListOfProduct(List<Product> products) {
        return products.parallelStream()
                .map(p -> p.getCodesMap()
                        .entrySet().stream()
                        .map(e -> new Job(p.getId(), e.getKey(), e.getValue()))
                )
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }
}
