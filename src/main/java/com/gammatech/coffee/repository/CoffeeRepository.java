package com.gammatech.coffee.repository;

import com.gammatech.coffee.entity.Coffee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends CrudRepository<Coffee, Integer> {
} 