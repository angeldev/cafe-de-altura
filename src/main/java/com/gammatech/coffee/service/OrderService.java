package com.gammatech.coffee.service;

import com.gammatech.coffee.entity.Order;
import com.gammatech.coffee.entity.OrderItem;
import com.gammatech.coffee.entity.Coffee;
import com.gammatech.coffee.entity.Customer;
import com.gammatech.coffee.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
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

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
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

        // Crear una nueva lista para los items con los cafés completos
        List<OrderItem> completedItems = new ArrayList<>();
        
        // Validar y cargar cada café
        for (OrderItem item : order.getItems()) {
            Optional<Coffee> coffee = coffeeService.getCoffeeById(item.getCoffee().getId());
            if (!coffee.isPresent() || item.getQuantity() <= 0) {
                return Optional.empty();
            }
            // Crear nuevo OrderItem con el café completo
            OrderItem completedItem = new OrderItem(coffee.get(), item.getQuantity());
            completedItems.add(completedItem);
        }

        // Crear el pedido con los datos completos
        Order completedOrder = new Order(customer.get(), completedItems);
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