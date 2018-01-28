package com.bslota.customermanager.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by bslota on 2018-01-12.
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> customers() {
        return customerService.findAll();
    }

    @GetMapping(params = "premium")
    public List<Customer> premiumCustomers() {
        return customerService.findAllPremium();
    }

    @GetMapping("/{id}")
    public Customer customer(@PathVariable Long id) {
        return customerService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        final Customer newCustomer = customerService.createCustomer(customer);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCustomer.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
