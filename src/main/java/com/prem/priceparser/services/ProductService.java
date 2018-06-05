package com.prem.priceparser.services;

import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.entity.User;
import com.prem.priceparser.exceptions.ExceptionErrorCode;
import com.prem.priceparser.exceptions.GenericBusinessException;
import com.prem.priceparser.rabbitmq.senders.RabbitMqSender;
import com.prem.priceparser.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 06.04.2018
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final RabbitMqSender<Job> inoundSender;

    @Transactional
    public Product createProduct(Product product) {
        log.debug("Creating product: {}", product);
        productRepository.save(product);
        log.debug("Product with id {} created", product.getId());
        return product;
    }


    public Product createProduct(Product product, User user) {
        product.setUser(user);
        return createProduct(product);
    }

    @Transactional
    public void deleteProduct(Product product) {
        log.debug("Deleting product with id <{}>", product.getId());
        productRepository.delete(product);
    }

    @Transactional
    public void deleteProduct(Long productId, User user) {
        log.debug("Deleting product with id <{}>", productId);
        Optional<Product> productOptional = productRepository.findByIdAndUser(productId, user);
        Product product = productOptional
                .orElseThrow(() -> new GenericBusinessException(ExceptionErrorCode.PRODUCT_NOT_FOUND));
        deleteProduct(product);
        log.debug("User with id {} deleted product with id {}", user.getId(), product.getId());
    }

    @Transactional
    public void updateProduct(Product product) {
        log.debug("Updating product with id <{}>", product.getId());
        productRepository.save(product);
        log.debug("Product with id {} was updated", product.getId());
    }

    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        log.debug("Searching product with id <{}>", id);
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> getAllByUser(User user) {
        log.debug("Getting products of user with id {}", user.getId());
        List<Product> products = productRepository.findAllByUser(user);
        log.debug("Found {} products of user with id {}", products.size(), user.getId());
        log.trace("Products: {}", products);
        return products;
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        log.debug("Getting all products");
        List<Product> products = (List<Product>) productRepository.findAll();
        log.debug("Found {} products", products.size());
        log.trace("Products: {}", products);
        return products;
    }

//    @Transactional
//    public void checkPrice(Long productId, User user) {
//        Product product = productRepository.findByIdAndUser(productId, user)
//                .orElseThrow(() -> new GenericBusinessException(ExceptionErrorCode.PRODUCT_NOT_FOUND));
////        parseProduct(product)
//                .forEach(inoundSender::sendMessageToQueue);
//    }

//    @Transactional
//    public void checkPrice(Long productId, User user) {
//        productRepository.findByIdAndUser(productId, user)
//                .map(this::parseProduct)
//                .map(l -> l.stream()
//                        .forEach())
//                .orElseThrow(() -> new GenericBusinessException(ExceptionErrorCode.PRODUCT_NOT_FOUND));
//    }

//    private List<Job> parseProduct(Product product) {
//        List<Job> jobs = new ArrayList<>();
//        product.getCodesMap()
//                .keySet()
//                .stream()
//                .forEach((k, v) -> jobs.add(new Job(product.getId(), k, v)));
//        return jobs;
//    }


}
