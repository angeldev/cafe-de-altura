package com.gammatech.coffee.service;

import com.gammatech.coffee.entity.Coffee;
import com.gammatech.coffee.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CoffeeService {
    
    private final CoffeeRepository coffeeRepository;

    @Autowired
    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public Page<Coffee> getAllCoffees(Pageable pageable) {
        return coffeeRepository.findAll(pageable);
    }

    public Coffee addCoffee(Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    public Optional<Coffee> getCoffeeById(int id) {
        return coffeeRepository.findById(id);
    }

    public Optional<Coffee> updateCoffee(int id, Coffee updatedCoffee) {
        return coffeeRepository.findById(id)
                .map(coffee -> {
                    updatedCoffee.setId(id);
                    return coffeeRepository.save(updatedCoffee);
                });
    }

    public Optional<Coffee> patchCoffee(int id, java.util.Map<String, Object> updates) {
        return coffeeRepository.findById(id)
                .map(coffee -> {
                    updates.forEach((key, value) -> {
                        switch (key) {
                            case "name":
                                coffee.setName((String) value);
                                break;
                            case "description":
                                coffee.setDescription((String) value);
                                break;
                            case "country":
                                coffee.setCountry((String) value);
                                break;
                            case "grindType":
                                coffee.setGrindType((String) value);
                                break;
                            case "roastLevel":
                                coffee.setRoastLevel((String) value);
                                break;
                            case "flavorNotes":
                                coffee.setFlavorNotes((String) value);
                                break;
                            case "weight":
                                coffee.setWeight((int) value);
                                break;
                            case "price":
                                coffee.setPrice(Double.parseDouble(value.toString()));
                                break;
                        }
                    });
                    return coffeeRepository.save(coffee);
                });
    }

    public Optional<Coffee> deleteCoffee(int id) {
        return coffeeRepository.findById(id)
                .map(coffee -> {
                    coffeeRepository.delete(coffee);
                    return coffee;
                });
    }
} 