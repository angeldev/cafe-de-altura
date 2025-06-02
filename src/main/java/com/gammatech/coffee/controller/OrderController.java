package com.gammatech.coffee.controller;

import com.gammatech.coffee.entity.Order;
import com.gammatech.coffee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private static final int PAGE_SIZE = 3;

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
    public ResponseEntity<PageResponse<Order>> getAllOrders(
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(orderService.getAllOrders(PageRequest.of(page, PAGE_SIZE))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.status(HttpStatus.OK).body(order))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
} 