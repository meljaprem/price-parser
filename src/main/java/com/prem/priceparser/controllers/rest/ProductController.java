package com.prem.priceparser.controllers.rest;


import com.prem.priceparser.domain.dto.ProductDto;
import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.entity.User;
import com.prem.priceparser.domain.enums.ScheduleType;
import com.prem.priceparser.domain.enums.ShopName;
import com.prem.priceparser.mappers.ProductMapper;
import com.prem.priceparser.services.ProductService;
import com.prem.priceparser.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final ProductMapper productMapper;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(name = "id") Long productId,
                                                  Authentication authentication) {
        User user = getUser(authentication);
        return new ResponseEntity<>(productService.getProductByIdAndUser(productId, user), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(Authentication authentication) {
        User user = getUser(authentication);
        return new ResponseEntity<>(productService.getAll(user), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<?> getAllProductsOfUser(Authentication authentication) {
        User user = getUser(authentication);
        List<Product> products = productService.getAllByUser(user);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Product> createProduct(@RequestParam(required = true) String name,
                                                 @RequestParam(required = false) Double expectedPrice,
                                                 Authentication authentication) {
        //TODO refactor it after testing
        User user = getUser(authentication);
        Product product = productService.createProduct(new Product(user, name, expectedPrice));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("{id}/addShop")
    public ResponseEntity<Product> addShopToProduct(@RequestParam(required = true) String shop,
                                                    @RequestParam(required = true) String code,
                                                    @PathVariable(required = true, name = "id") Long productId,
                                                    Authentication authentication) {
        //TODO refactor it after testing
        User user = getUser(authentication);
        Product product = productService.addShop(user, productId, ShopName.valueOf(shop), code);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("{id}/checkPrice")
    public ResponseEntity<?> checkPriceProduct(@PathVariable(required = true, name = "id") Long productId,
                                               Authentication authentication) {
        //TODO refactor it after testing
        User user = getUser(authentication);
        productService.checkPrice(productId, user);
        return new ResponseEntity<>("Request for checking was sent!", HttpStatus.OK);
    }

    @PostMapping("{id}/scheduleActive")
    public ResponseEntity<?> checkPriceProduct(@PathVariable(required = true, name = "id") Long productId,
                                               @RequestParam(required = true, name = "active") Boolean active,
                                               @RequestParam(required = false) String scheduleType,
                                               Authentication authentication) {
        //TODO refactor it after testing
        User user = getUser(authentication);
        Product product = null;
        if (active && (scheduleType == null || scheduleType.length() < 1)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (scheduleType == null || scheduleType.length() < 1) {
            product = productService.switchScheduler(user, productId, active, null);
        } else {
            product = productService.switchScheduler(user, productId, active, ScheduleType.valueOf(scheduleType));
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> updateProduct(ProductDto productDto,
                                                    Authentication authentication) {
        //TODO refactor it after testing
        User user = getUser(authentication);
        Product product = productService.createProduct(productMapper.convertFromDto(productDto), user);
        return new ResponseEntity<>(productMapper.convertToDto(product), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestParam(required = true, name = "id") Long productId,
                                           Authentication authentication) {
        User user = getUser(authentication);
        productService.deleteProduct(productId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User getUser(Authentication authentication) {
        User user;
        if (authentication != null) {
            user = (User) authentication.getPrincipal();
            log.debug("Authentication is not null, user = {}", user);
        } else {
            user = userService.getUserByUsername("admin");
            log.debug("Authentication is null, user from DB = {}", user);
        }
        return user;
    }
}
