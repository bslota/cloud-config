package com.bslota.customermanager.customer;

import com.bslota.customermanager.infrastructure.exception.BadRequestException;
import com.bslota.customermanager.infrastructure.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by bslota on 2018-01-12.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    List<Customer> findAll() {
        return customerRepository.findAll();
    }

    Customer findOne(Long id) {
        return Optional.ofNullable(customerRepository.findOne(id)).orElseThrow(customerNotFound(id));
    }

    Customer createCustomer(Customer from) {
        if (customerRepository.findByNameAndEmail(from.getName(), from.getEmail()).isPresent()) {
            throw new BadRequestException(String.format("Customer with name %s and email %s already exists", from.getName(), from.getEmail()));
        } else {
            return customerRepository.save(new Customer(from.getName(), from.getEmail()));
        }
    }

    private Supplier<NotFoundException> customerNotFound(Long id) {
        return () -> new NotFoundException(String.format("Customer with id %n not found", id));
    }
}
