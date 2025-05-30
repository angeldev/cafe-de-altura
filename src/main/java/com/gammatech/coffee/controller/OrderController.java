package com.gammatech.coffee.controller;

import com.gammatech.coffee.entity.Order;
import com.gammatech.coffee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        if (!orderService.isValidOrder(order)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        return orderService.createOrder(order)
                .map(newOrder -> ResponseEntity.status(HttpStatus.CREATED).body(newOrder))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.status(HttpStatus.OK).body(order))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
} 