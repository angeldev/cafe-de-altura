package com.gammatech.coffee.controller;

import com.gammatech.coffee.entity.Coffee;
import com.gammatech.coffee.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coffees")
public class CoffeeController {

    private final CoffeeService coffeeService;

    @Autowired
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }
	
    @GetMapping
    public ResponseEntity<List<Coffee>> getCoffees() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(coffeeService.getAllCoffees());
    }

    @PostMapping
    public ResponseEntity<Coffee> addCoffee(@RequestBody Coffee coffee) {
        Coffee newCoffee = coffeeService.addCoffee(coffee);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newCoffee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Coffee> deleteCoffee(@PathVariable int id) {
        return coffeeService.deleteCoffee(id)
                .map(coffee -> ResponseEntity.status(HttpStatus.OK).body(coffee))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coffee> updateCoffee(@PathVariable int id, @RequestBody Coffee updatedCoffee) {
        return coffeeService.updateCoffee(id, updatedCoffee)
                .map(coffee -> ResponseEntity.status(HttpStatus.OK).body(coffee))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Coffee> patchCoffee(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        return coffeeService.patchCoffee(id, updates)
                .map(coffee -> ResponseEntity.status(HttpStatus.OK).body(coffee))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
