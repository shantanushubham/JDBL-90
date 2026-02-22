package org.geeksforgeeks.food_ordering_app.controllers;

import org.geeksforgeeks.food_ordering_app.dto.request.OrderCreateRequest;
import org.geeksforgeeks.food_ordering_app.dto.response.MostOrderedItemResponse;
import org.geeksforgeeks.food_ordering_app.dto.response.OrderCreateResponse;
import org.geeksforgeeks.food_ordering_app.entities.Order;
import org.geeksforgeeks.food_ordering_app.entities.OrderStatus;
import org.geeksforgeeks.food_ordering_app.mapper.OrderMapper;
import org.geeksforgeeks.food_ordering_app.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    private OrderController orderController;

    @BeforeEach
    void setUp() {
        orderController = new OrderController(orderService, orderMapper);
    }

    @Test
    void createOrder_returnsCreated() {
        OrderCreateRequest request = new OrderCreateRequest();
        Order order = Order.builder().id(UUID.randomUUID()).build();
        OrderCreateResponse response = new OrderCreateResponse();
        when(orderService.createOrder(any(OrderCreateRequest.class))).thenReturn(order);
        when(orderMapper.toOrderCreateResponse(order)).thenReturn(response);

        ResponseEntity<?> result = orderController.createOrder(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertSame(response, result.getBody());
    }

    @Test
    void createOrder_returns500_onException() {
        when(orderService.createOrder(any(OrderCreateRequest.class))).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> result = orderController.createOrder(new OrderCreateRequest());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getOrderById_returnsOk() {
        UUID id = UUID.randomUUID();
        Order order = Order.builder().id(id).build();
        when(orderService.getOrderById(id)).thenReturn(order);

        ResponseEntity<?> result = orderController.getOrderById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertSame(order, result.getBody());
    }

    @Test
    void getOrderById_returns500_onException() {
        UUID id = UUID.randomUUID();
        when(orderService.getOrderById(id)).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> result = orderController.getOrderById(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getAllOrders_returnsOk() {
        when(orderService.getAllOrders()).thenReturn(List.of());

        ResponseEntity<?> result = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAllOrders_returns500_onException() {
        when(orderService.getAllOrders()).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> result = orderController.getAllOrders();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getOrdersByCustomerId_returnsOk() {
        UUID customerId = UUID.randomUUID();
        when(orderService.getOrdersByCustomerId(customerId)).thenReturn(List.of());

        ResponseEntity<?> result = orderController.getOrdersByCustomerId(customerId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getOrdersByCustomerId_returns500_onException() {
        UUID customerId = UUID.randomUUID();
        when(orderService.getOrdersByCustomerId(customerId)).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> result = orderController.getOrdersByCustomerId(customerId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getOrdersByRestaurantId_returnsOk() {
        UUID restaurantId = UUID.randomUUID();
        when(orderService.getOrdersByRestaurantId(restaurantId)).thenReturn(List.of());

        ResponseEntity<?> result = orderController.getOrdersByRestaurantId(restaurantId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getOrdersByRestaurantId_returns500_onException() {
        UUID restaurantId = UUID.randomUUID();
        when(orderService.getOrdersByRestaurantId(restaurantId)).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> result = orderController.getOrdersByRestaurantId(restaurantId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getMostOrderedItem_returnsOk_whenFound() {
        UUID restaurantId = UUID.randomUUID();
        MostOrderedItemResponse response = MostOrderedItemResponse.builder().quantityOrdered(10L).build();
        when(orderService.getMostOrderedItemInLast30Days(restaurantId)).thenReturn(Optional.of(response));

        ResponseEntity<?> result = orderController.getMostOrderedItem(restaurantId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getMostOrderedItem_returns404_whenEmpty() {
        UUID restaurantId = UUID.randomUUID();
        when(orderService.getMostOrderedItemInLast30Days(restaurantId)).thenReturn(Optional.empty());

        ResponseEntity<?> result = orderController.getMostOrderedItem(restaurantId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getMostOrderedItem_returns500_onException() {
        UUID restaurantId = UUID.randomUUID();
        when(orderService.getMostOrderedItemInLast30Days(restaurantId)).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> result = orderController.getMostOrderedItem(restaurantId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void updateOrder_returnsOk() {
        UUID id = UUID.randomUUID();
        Order order = Order.builder().id(id).status(OrderStatus.DELIVERED).build();
        when(orderService.updateOrder(eq(id), any(Order.class))).thenReturn(order);

        Order body = new Order();
        body.setStatus(OrderStatus.DELIVERED);
        body.setTotalAmount(10.0);
        ResponseEntity<?> result = orderController.updateOrder(id, body);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void updateOrder_returns404_onRuntimeException() {
        UUID id = UUID.randomUUID();
        when(orderService.updateOrder(eq(id), any(Order.class))).thenThrow(new RuntimeException("not found"));

        ResponseEntity<?> result = orderController.updateOrder(id, new Order());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteOrder_returnsNoContent() {
        UUID id = UUID.randomUUID();

        ResponseEntity<?> result = orderController.deleteOrder(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(orderService).deleteOrder(id);
    }

    @Test
    void deleteOrder_returns404_onRuntimeException() {
        UUID id = UUID.randomUUID();
        doThrow(new RuntimeException("not found")).when(orderService).deleteOrder(id);

        ResponseEntity<?> result = orderController.deleteOrder(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
