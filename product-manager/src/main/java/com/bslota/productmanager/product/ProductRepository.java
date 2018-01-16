package com.bslota.productmanager.product;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bslota on 2018-01-13.
 */
interface ProductRepository extends JpaRepository<Product, String> {
}
