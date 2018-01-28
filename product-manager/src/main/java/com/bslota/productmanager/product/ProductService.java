package com.bslota.productmanager.product;

import com.bslota.productmanager.infrastructure.exception.BadRequestException;
import com.bslota.productmanager.infrastructure.exception.NotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by bslota on 2018-01-13.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSubscriptionRepository productSubscriptionRepository;
    private final ProductConfig productConfig;

    ProductService(ProductRepository productRepository, ProductSubscriptionRepository productSubscriptionRepository, ProductConfig productConfig) {
        this.productRepository = productRepository;
        this.productSubscriptionRepository = productSubscriptionRepository;
        this.productConfig = productConfig;
    }


    List<Product> findAll() {
        return productRepository.findAll();
    }

    Product findOne(String code) {
        return Optional.ofNullable(productRepository.findOne(code)).orElseThrow(productNotFoundException(code));
    }

    List<Product> findForCustomer(Long customerId) {
        return productSubscriptionRepository.findByIdCustomerId(customerId)
                .stream()
                .map(ProductSubscription::getId)
                .map(ProductSubscriptionId::getProduct)
                .collect(Collectors.toList());
    }

    Product findForCustomer(Long customerId, String productCode) {
        return productSubscriptionRepository.findByIdCustomerIdAndIdProductCode(customerId, productCode)
                .orElseThrow(productNotFoundException(customerId, productCode))
                .getId().getProduct();
    }

    @Transactional
    ProductSubscription subscribeToProduct(Long customerId, String productCode) {
        final Product product = findOne(productCode);
        if (productSubscriptionRepository.findByIdCustomerIdAndIdProductCode(customerId, productCode).isPresent()) {
            throw new BadRequestException(String.format("Assignment for customer with id %d and product %s already exists", customerId, productCode));
        } else {
            final ProductSubscriptionId newId = new ProductSubscriptionId(customerId, product);
            return productSubscriptionRepository.save(new ProductSubscription(newId));
        }
    }

    List<String> findExclusives() {
        return productConfig.getExclusives();
    }

    private Supplier<NotFoundException> productNotFoundException(String code) {
        return () -> new NotFoundException(String.format("Product with code %s not found", code));
    }

    private Supplier<NotFoundException> productNotFoundException(Long customerId, String productCode) {
        return () -> new NotFoundException(String.format("Product with code %s for customer %d not found", productCode, customerId));
    }
}
