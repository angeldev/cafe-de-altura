package com.gammatech.coffee.service;

import com.gammatech.coffee.entity.Order;
import com.gammatech.coffee.entity.OrderItem;
import com.gammatech.coffee.entity.Coffee;
import com.gammatech.coffee.entity.Customer;
import com.gammatech.coffee.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final CoffeeService coffeeService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerService customerService, CoffeeService coffeeService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.coffeeService = coffeeService;
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> createOrder(Order order) {
        // Obtener el cliente completo
        Optional<Customer> customer = customerService.getCustomerById(order.getCustomer().getId());
        if (!customer.isPresent()) {
            return Optional.empty();
        }

        // Usar un Map para combinar items del mismo café
        Map<Integer, OrderItem> combinedItems = new HashMap<>();
        
        // Validar y cargar cada café
        for (OrderItem item : order.getItems()) {
            Optional<Coffee> coffee = coffeeService.getCoffeeById(item.getCoffee().getId());
            if (!coffee.isPresent() || item.getQuantity() <= 0) {
                return Optional.empty();
            }

            // Si ya existe un item para este café, sumar las cantidades
            if (combinedItems.containsKey(coffee.get().getId())) {
                OrderItem existingItem = combinedItems.get(coffee.get().getId());
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            } else {
                // Si no existe, crear nuevo item
                OrderItem newItem = new OrderItem(coffee.get(), item.getQuantity());
                combinedItems.put(coffee.get().getId(), newItem);
            }
        }

        // Crear una lista con los items combinados
        List<OrderItem> finalItems = new ArrayList<>(combinedItems.values());

        // Crear el pedido con los datos completos
        Order completedOrder = new Order(customer.get(), finalItems);
        completedOrder = orderRepository.save(completedOrder);
        
        return Optional.of(completedOrder);
    }

    public boolean isValidOrder(Order order) {
        return order != null &&
               order.getCustomer() != null &&
               order.getItems() != null &&
               !order.getItems().isEmpty() &&
               order.getItems().stream().allMatch(item -> 
                   item.getCoffee() != null && 
                   item.getQuantity() > 0
               );
    }
} 