package com.prem.priceparser.services;

import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public Product createProduct(Product product){
        log.debug("Creating product: {}", product);
        productRepository.save(product);
        log.debug("Product with id {} created", product.getId());
        return product;
    }

    public void deleteProduct(Product product){
        log.debug("Deleting product with id <{}>", product.getId());
        productRepository.delete(product);
    }

    public void deleteProduct(Long productId){
        log.debug("Deleting product with id <{}>", productId);
        productRepository.deleteById(productId);
    }

    public void updateProduct(Product product){
        log.debug("Updating product with id <{}>", product.getId());
        productRepository.save(product);
        log.debug("Product with id {} was updated", product.getId());
    }

    public Optional<Product> getProductById(Long id){
        log.debug("Finding product with id <{}>", id);
        return productRepository.findById(id);
    }

    public List<Product> getAllByUserId(Long userId){
        log.debug("Getting products of user with id {}", userId);
        List<Product> products = productRepository.findAllByUser(userId);
        log.debug("Found {} product of user with id {}", products.size(), userId);
        log.trace("Products: {}", products);
        return products;
    }
}
