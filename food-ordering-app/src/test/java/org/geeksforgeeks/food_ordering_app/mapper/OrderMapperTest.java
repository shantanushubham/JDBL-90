package org.geeksforgeeks.food_ordering_app.mapper;

import org.geeksforgeeks.food_ordering_app.dto.response.OrderCreateResponse;
import org.geeksforgeeks.food_ordering_app.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class OrderMapperTest {

    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapper();
    }

    @Test
    void toOrderCreateResponse_returnsNull_whenOrderNull() {
        Assertions.assertNull(orderMapper.toOrderCreateResponse(null));
    }

    @Test
    void toOrderCreateResponse_mapsOrder_withNullAssociations() {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .orderDate(java.time.LocalDateTime.now())
                .status(OrderStatus.ORDERED)
                .totalAmount(null)
                .orderItems(new ArrayList<>())
                .build();
        order.setRestaurant(null);
        order.setCustomer(null);
        order.setDeliveryAddress(null);

        OrderCreateResponse response = orderMapper.toOrderCreateResponse(order);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(order.getId(), response.getOrderId());
        Assertions.assertNull(response.getRestaurantName());
        Assertions.assertNull(response.getCustomerName());
        Assertions.assertEquals(0.0, response.getOrderTotal());
        Assertions.assertTrue(response.getItems().isEmpty());
    }

    @Test
    void toOrderCreateResponse_mapsOrder_withRestaurantAndCustomer() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Pizza Hut");
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .restaurant(restaurant)
                .customer(customer)
                .totalAmount(25.0)
                .orderItems(new ArrayList<>())
                .build();

        OrderCreateResponse response = orderMapper.toOrderCreateResponse(order);

        Assertions.assertEquals("Pizza Hut", response.getRestaurantName());
        Assertions.assertEquals("John Doe", response.getCustomerName());
        Assertions.assertEquals(25.0, response.getOrderTotal());
    }

    @Test
    void toOrderCreateResponse_mapsOrderItems_withNullMenuItemAndQuantity() {
        OrderItem itemNullMenuItem = OrderItem.builder()
                .menuItem(null)
                .quantity(2)
                .build();
        OrderItem itemNullQuantity = OrderItem.builder()
                .menuItem(new MenuItem())
                .quantity(null)
                .build();
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .orderItems(new ArrayList<>(List.of(itemNullMenuItem, itemNullQuantity)))
                .build();
        itemNullMenuItem.setOrder(order);
        itemNullQuantity.setOrder(order);

        OrderCreateResponse response = orderMapper.toOrderCreateResponse(order);

        Assertions.assertEquals(2, response.getItems().size());
        // First item: null menuItem, quantity 2 -> quantity 2, amount 0
        Assertions.assertEquals(2, response.getItems().get(0).getQuantity());
        Assertions.assertEquals(0.0, response.getItems().get(0).getAmount());
        // Second item: null quantity -> quantity 0, amount 0
        Assertions.assertEquals(0, response.getItems().get(1).getQuantity());
        Assertions.assertEquals(0.0, response.getItems().get(1).getAmount());
    }

    @Test
    void toOrderCreateResponse_mapsOrderItem_withFullMenuItemAndQuantity() {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Margherita");
        menuItem.setPrice(12.5);
        OrderItem orderItem = OrderItem.builder()
                .menuItem(menuItem)
                .quantity(2)
                .build();
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .orderItems(new ArrayList<>(List.of(orderItem)))
                .build();
        orderItem.setOrder(order);

        OrderCreateResponse response = orderMapper.toOrderCreateResponse(order);

        Assertions.assertEquals(1, response.getItems().size());
        Assertions.assertEquals("Margherita", response.getItems().get(0).getItemName());
        Assertions.assertEquals(2, response.getItems().get(0).getQuantity());
        Assertions.assertEquals(25.0, response.getItems().get(0).getAmount());
    }

    @Test
    void toOrderCreateResponse_handlesNullOrderItemInList() {
        List<OrderItem> items = new ArrayList<>();
        items.add(null);
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .orderItems(items)
                .build();

        OrderCreateResponse response = orderMapper.toOrderCreateResponse(order);

        Assertions.assertEquals(1, response.getItems().size());
        Assertions.assertNull(response.getItems().get(0));
    }
}
