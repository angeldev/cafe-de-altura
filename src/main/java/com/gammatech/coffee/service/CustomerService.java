package com.gammatech.coffee.service;

import com.gammatech.coffee.entity.Customer;
import com.gammatech.coffee.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomerService {
    
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> updateCustomer(int id, Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(customer -> {
                    updatedCustomer.setId(id);
                    return customerRepository.save(updatedCustomer);
                });
    }

    public Optional<Customer> deleteCustomer(int id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customerRepository.delete(customer);
                    return customer;
                });
    }

    public boolean isValidCustomer(Customer customer) {
        return customer != null &&
               customer.getName() != null && !customer.getName().trim().isEmpty() &&
               customer.getEmail() != null && !customer.getEmail().trim().isEmpty() &&
               customer.getPhone() != null && !customer.getPhone().trim().isEmpty() &&
               customer.getAddress() != null && !customer.getAddress().trim().isEmpty();
    }
} 