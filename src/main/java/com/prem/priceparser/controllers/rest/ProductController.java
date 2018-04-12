package com.prem.priceparser.controllers.rest;


import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.entity.User;
import com.prem.priceparser.services.ProductService;
import com.prem.priceparser.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable() Long productId) {
        Optional<Product> productOptional = productService.getProductById(productId);
        return productOptional
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(Authentication authentication) {
        List<Product> products = productService.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Product> createProduct(@RequestParam(required = true) String name,
                                                 @RequestParam(required = false) Float expectedPrice,
                                                 Authentication authentication) {
        //TODO refactor it after testing
        User user = getUser(authentication);
        Product product = productService.createProduct(new Product(user, name, expectedPrice));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestParam(required = true) Long productId,
                                           Authentication authentication) {
        User user = getUser(authentication);
        productService.deleteProduct(productId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private User getUser(Authentication authentication) {
        User user;
        if(authentication != null){
            user = (User) authentication.getPrincipal();
            log.debug("authentication is not null, user = {}", user);
        } else {
            user = userService.getUserByUsername("admin");
            log.debug("authentication is  null, user from DB = {}", user);
        }
        return user;
    }
}
