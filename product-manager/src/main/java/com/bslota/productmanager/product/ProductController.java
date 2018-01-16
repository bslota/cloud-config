package com.bslota.productmanager.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by bslota on 2018-01-13.
 */
@RestController
@RequestMapping
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    List<Product> products() {
        return productService.findAll();
    }

    @GetMapping("/products/{code}")
    Product product(@PathVariable String code) {
        return productService.findOne(code);
    }

    @GetMapping("/customers/{customerId}/products")
    List<Product> customerProducts(@PathVariable Long customerId) {
        return productService.findForCustomer(customerId);
    }

    @GetMapping("/customers/{customerId}/products/{productCode}")
    Product customerProducts(@PathVariable Long customerId, @PathVariable String productCode) {
        return productService.findForCustomer(customerId, productCode);
    }

    @PostMapping("/customers/{customerId}/products")
    public ResponseEntity<?> create(@PathVariable Long customerId, @RequestParam String productCode) {
        final ProductSubscription productSubscription = productService.subscribeToProduct(customerId, productCode);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{code}")
                .buildAndExpand(productSubscription.getId().getProduct().getCode())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
