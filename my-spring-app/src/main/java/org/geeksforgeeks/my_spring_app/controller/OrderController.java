package org.geeksforgeeks.my_spring_app.controller;

import lombok.extern.slf4j.Slf4j;
import org.geeksforgeeks.my_spring_app.dto.CreateOrderRequest;
import org.geeksforgeeks.my_spring_app.entities.Order;
import org.geeksforgeeks.my_spring_app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        log.info("OrderController initialized with OrderService: {}", orderService);
    }

    /**
     * Create a new order.
     * 
     * @param createOrderRequest The order details.
     * @return The created order.
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        log.info("Request to create order: {}", createOrderRequest);
        Order createdOrder = this.orderService.addOrderFromRequest(createOrderRequest);
        log.info("Order created successfully with ID: {}", createdOrder.getId());
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * Get all orders.
     * 
     * @return List of all orders.
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        log.info("Request to fetch all orders");
        List<Order> orders = this.orderService.getAllOrders();
        log.info("Retrieved {} orders", orders.size());
        return ResponseEntity.ok(orders);
    }

    /**
     * Get order by ID.
     * 
     * @param id The order ID (UUID).
     * @return The order details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        log.info("Request to fetch order with ID: {}", id);
        Order order = this.orderService.getOrderById(id);
        log.info("Order retrieved: {}", order);
        return ResponseEntity.ok(order);
    }

    /**
     * Get orders by User ID.
     * 
     * @param userId The user ID (UUID).
     * @return List of orders for the user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable UUID userId) {
        log.info("Request to fetch orders for User ID: {}", userId);
        List<Order> orders = this.orderService.getOrderByUserId(userId);
        log.info("Retrieved {} orders for User ID: {}", orders.size(), userId);
        return ResponseEntity.ok(orders);
    }

    /**
     * Delete an order by ID.
     * 
     * @param id The order ID (UUID).
     * @return No content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        log.info("Request to delete order with ID: {}", id);
        this.orderService.deleteOrder(id);
        log.info("Order deleted successfully: {}", id);
        return ResponseEntity.noContent().build();
    }
}
