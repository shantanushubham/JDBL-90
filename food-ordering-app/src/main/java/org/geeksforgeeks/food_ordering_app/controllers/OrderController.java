package org.geeksforgeeks.food_ordering_app.controllers;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.food_ordering_app.dto.request.OrderCreateRequest;
import org.geeksforgeeks.food_ordering_app.dto.response.MostOrderedItemResponse;
import org.geeksforgeeks.food_ordering_app.entities.Order;
import org.geeksforgeeks.food_ordering_app.mapper.OrderMapper;
import org.geeksforgeeks.food_ordering_app.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        try {
            Order createdOrder = this.orderService.createOrder(orderCreateRequest);
            return new ResponseEntity<>(this.orderMapper.toOrderCreateResponse(createdOrder), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(this.orderService.getOrderById(id));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = this.orderService.getAllOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable UUID customerId) {
        try {
            List<Order> orders = this.orderService.getOrdersByCustomerId(customerId);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> getOrdersByRestaurantId(@PathVariable UUID restaurantId) {
        try {
            List<Order> orders = this.orderService.getOrdersByRestaurantId(restaurantId);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/restaurant/{restaurantId}/most-ordered-item")
    public ResponseEntity<?> getMostOrderedItem(@PathVariable UUID restaurantId) {
        try {
            return this.orderService.getMostOrderedItemInLast30Days(restaurantId)
                    .map(ResponseEntity::<MostOrderedItemResponse>ok)
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable UUID id, @RequestBody Order orderDetails) {
        try {
            Order updatedOrder = this.orderService.updateOrder(id, orderDetails);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
        try {
            this.orderService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
