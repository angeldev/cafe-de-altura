package com.gammatech.coffee.controller;

import com.gammatech.coffee.entity.Customer;
import com.gammatech.coffee.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private static final int PAGE_SIZE = 3;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
	
    @GetMapping
    public ResponseEntity<PageResponse<Customer>> getCustomers(
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(customerService.getAllCustomers(PageRequest.of(page, PAGE_SIZE))));
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        if (!customerService.isValidCustomer(customer)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Customer newCustomer = customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body(newCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        if (!customerService.isValidCustomer(customer)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return customerService.updateCustomer(id, customer)
                .map(updatedCustomer -> ResponseEntity.status(HttpStatus.OK).body(updatedCustomer))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        return customerService.deleteCustomer(id)
                .map(customer -> ResponseEntity.status(HttpStatus.OK).body(customer))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
} 