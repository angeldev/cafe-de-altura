package com.gammatech.coffee.repository;

import com.gammatech.coffee.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>, CrudRepository<Customer, Integer> {
} 