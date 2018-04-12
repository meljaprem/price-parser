package com.prem.priceparser.services;

import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.entity.User;
import com.prem.priceparser.repository.ProductRepository;
import com.prem.priceparser.repository.UserRepository;
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

    public void deleteProduct(Long productId, User user){
        log.debug("Deleting product with id <{}>", productId);
        Optional<Product> productOptional = productRepository.findByIdAndUser(productId, user);
        productOptional.ifPresent(product -> productRepository.deleteById(productId));
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

    public List<Product> getAllByUser(User user){
        log.debug("Getting products of user with id {}", user.getId());
        List<Product> products = productRepository.findAllByUser(user);
        log.debug("Found {} products of user with id {}", products.size(), user.getId());
        log.trace("Products: {}", products);
        return products;
    }

    public List<Product> getAll(){
        log.debug("Getting all products");
        List<Product> products = (List<Product>) productRepository.findAll();
        log.debug("Found {} products", products.size());
        log.trace("Products: {}", products);
        return products;
    }
}
