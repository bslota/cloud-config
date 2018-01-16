package com.bslota.customermanager.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by bslota on 2018-01-12.
 */
interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByNameAndEmail(String name, String email);
}
