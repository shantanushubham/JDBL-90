package org.geeksforgeeks.food_ordering_app.service;

import org.geeksforgeeks.food_ordering_app.dto.request.OrderCreateRequest;
import org.geeksforgeeks.food_ordering_app.dto.request.OrderItemCreateRequest;
import org.geeksforgeeks.food_ordering_app.dto.response.MostOrderedItemResponse;
import org.geeksforgeeks.food_ordering_app.entities.*;
import org.geeksforgeeks.food_ordering_app.exceptions.NotFoundException;
import org.geeksforgeeks.food_ordering_app.repository.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class OrderServiceTest {

    @MockitoBean
    private OrderRepository orderRepository;

    @MockitoBean
    private OrderItemRepository orderItemRepository;

    @MockitoBean
    private CustomerRepository customerRepository;

    @MockitoBean
    private RestaurantRepository restaurantRepository;

    @MockitoBean
    private AddressRepository addressRepository;

    @MockitoBean
    private MenuItemRepository menuItemRepository;

    @Autowired
    private OrderService orderService;

    // ---------- createOrder (from request) ----------

    @Test
    void createOrder_fromRequest_buildsOrderAndSetsTotal() {
        UUID customerId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        UUID menuItemId1 = UUID.randomUUID();
        UUID menuItemId2 = UUID.randomUUID();

        // Request
        OrderItemCreateRequest item1 = new OrderItemCreateRequest();
        item1.setMenuItemId(menuItemId1);
        item1.setQuantity(2);

        OrderItemCreateRequest item2 = new OrderItemCreateRequest();
        item2.setMenuItemId(menuItemId2);
        item2.setQuantity(3);

        OrderCreateRequest request = new OrderCreateRequest();
        request.setCustomerId(customerId);
        request.setRestaurantId(restaurantId);
        request.setAddressId(addressId);
        request.setItemQuantityList(List.of(item1, item2));

        // Entities returned by repositories
        Customer customer = Customer.builder().id(customerId).build();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        Address address = new Address();
        address.setId(addressId);

        MenuItem menuItem1 = new MenuItem();
        menuItem1.setId(menuItemId1);
        menuItem1.setPrice(10.0);
        MenuItem menuItem2 = new MenuItem();
        menuItem2.setId(menuItemId2);
        menuItem2.setPrice(20.0);

        Mockito.when(customerRepository.findById(customerId)).thenReturn(customer);
        Mockito.when(restaurantRepository.findById(restaurantId)).thenReturn(restaurant);
        Mockito.when(addressRepository.findById(addressId)).thenReturn(address);
        Mockito.when(menuItemRepository.findAllByIdList(Mockito.any()))
                .thenReturn(List.of(menuItem1, menuItem2));

        Mockito.when(orderRepository.save(Mockito.any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.createOrder(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(customer, result.getCustomer());
        Assertions.assertEquals(restaurant, result.getRestaurant());
        Assertions.assertEquals(address, result.getDeliveryAddress());
        Assertions.assertEquals(2, result.getOrderItems().size());

        double expectedTotal = 2 * 10.0 + 3 * 20.0;
        Assertions.assertEquals(expectedTotal, result.getTotalAmount());
    }

    // ---------- getOrderById ----------

    @Test
    void getOrderById_returnsOrder_whenFound() {
        UUID orderId = UUID.randomUUID();
        Order order = Order.builder().id(orderId).build();

        Mockito.when(orderRepository.findById(orderId)).thenReturn(order);

        Order result = orderService.getOrderById(orderId);

        Assertions.assertSame(order, result);
    }

    @Test
    void getOrderById_throwsNotFound_whenRepositoryThrows() {
        UUID orderId = UUID.randomUUID();

        Mockito.when(orderRepository.findById(orderId))
                .thenThrow(new NotFoundException(Order.class, "id", orderId));

        Assertions.assertThrows(NotFoundException.class,
                () -> orderService.getOrderById(orderId));
    }

    // ---------- simple list methods ----------

    @Test
    void getAllOrders_delegatesToRepository() {
        List<Order> orders = List.of(Order.builder().build(), Order.builder().build());
        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        Assertions.assertSame(orders, result);
    }

    @Test
    void getOrdersByCustomerId_delegatesToRepository() {
        UUID customerId = UUID.randomUUID();
        List<Order> orders = new ArrayList<>();
        Mockito.when(orderRepository.findByCustomerId(customerId)).thenReturn(orders);

        List<Order> result = orderService.getOrdersByCustomerId(customerId);

        Assertions.assertSame(orders, result);
    }

    @Test
    void getOrdersByRestaurantId_delegatesToRepository() {
        UUID restaurantId = UUID.randomUUID();
        List<Order> orders = new ArrayList<>();
        Mockito.when(orderRepository.findByRestaurantId(restaurantId)).thenReturn(orders);

        List<Order> result = orderService.getOrdersByRestaurantId(restaurantId);

        Assertions.assertSame(orders, result);
    }

    // ---------- getMostOrderedItemInLast30Days (in-memory) ----------

    @Test
    void getMostOrderedItemInLast30Days_returnsEmpty_whenNoOrders() {
        UUID restaurantId = UUID.randomUUID();
        Mockito.when(orderRepository.findByRestaurantIdAndOrderDateAfter(
                        Mockito.eq(restaurantId), Mockito.any()))
                .thenReturn(Collections.emptyList());

        Optional<MostOrderedItemResponse> result =
                orderService.getMostOrderedItemInLast30Days(restaurantId);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getMostOrderedItemInLast30Days_returnsEmpty_whenTopMenuItemNotFound() {
        UUID restaurantId = UUID.randomUUID();
        UUID menuItemId = UUID.randomUUID();

        MenuItem menuItem = new MenuItem();
        menuItem.setId(menuItemId);

        OrderItem orderItem = OrderItem.builder()
                .menuItem(menuItem)
                .quantity(5)
                .build();

        Order order = Order.builder().build();
        order.setOrderItems(List.of(orderItem));

        Mockito.when(orderRepository.findByRestaurantIdAndOrderDateAfter(
                        Mockito.eq(restaurantId), Mockito.any()))
                .thenReturn(List.of(order));
        Mockito.when(menuItemRepository.findById(menuItemId))
                .thenReturn(Optional.empty());

        Optional<MostOrderedItemResponse> result =
                orderService.getMostOrderedItemInLast30Days(restaurantId);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getMostOrderedItemInLast30Days_returnsMostOrderedItem() {
        UUID restaurantId = UUID.randomUUID();
        UUID menuItemId1 = UUID.randomUUID();
        UUID menuItemId2 = UUID.randomUUID();

        MenuItem menuItem1 = new MenuItem();
        menuItem1.setId(menuItemId1);
        menuItem1.setName("Item1");
        menuItem1.setPrice(10.0);

        MenuItem menuItem2 = new MenuItem();
        menuItem2.setId(menuItemId2);
        menuItem2.setName("Item2");
        menuItem2.setPrice(5.0);

        OrderItem orderItem1 = OrderItem.builder()
                .menuItem(menuItem1)
                .quantity(5)
                .build();
        OrderItem orderItem2 = OrderItem.builder()
                .menuItem(menuItem2)
                .quantity(3)
                .build();

        Order order = Order.builder().build();
        order.setOrderItems(List.of(orderItem1, orderItem2));

        Mockito.when(orderRepository.findByRestaurantIdAndOrderDateAfter(
                        Mockito.eq(restaurantId), Mockito.any()))
                .thenReturn(List.of(order));
        Mockito.when(menuItemRepository.findById(menuItemId1))
                .thenReturn(Optional.of(menuItem1));

        Optional<MostOrderedItemResponse> result =
                orderService.getMostOrderedItemInLast30Days(restaurantId);

        Assertions.assertTrue(result.isPresent());
        MostOrderedItemResponse response = result.get();
        Assertions.assertEquals(menuItemId1, response.getMenuItemId());
        Assertions.assertEquals("Item1", response.getName());
        Assertions.assertEquals(5L, response.getQuantityOrdered());
    }

    // ---------- getMostOrderedItemInLast30DaysFromDb (DB-only) ----------

    @Test
    void getMostOrderedItemInLast30DaysFromDb_returnsEmpty_whenRowsNull() {
        UUID restaurantId = UUID.randomUUID();
        Mockito.when(orderItemRepository.findMostOrderedItemInLast30DaysNative(
                        Mockito.eq(restaurantId), Mockito.any()))
                .thenReturn(null);

        Optional<MostOrderedItemResponse> result =
                orderService.getMostOrderedItemInLast30DaysFromDb(restaurantId);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getMostOrderedItemInLast30DaysFromDb_returnsEmpty_whenRowsEmpty() {
        UUID restaurantId = UUID.randomUUID();
        Mockito.when(orderItemRepository.findMostOrderedItemInLast30DaysNative(
                        Mockito.eq(restaurantId), Mockito.any()))
                .thenReturn(new ArrayList<>());

        Optional<MostOrderedItemResponse> result =
                orderService.getMostOrderedItemInLast30DaysFromDb(restaurantId);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getMostOrderedItemInLast30DaysFromDb_returnsEmpty_whenRowMalformed() {
        UUID restaurantId = UUID.randomUUID();
        List<Object[]> rows = new ArrayList<>();
        rows.add(new Object[]{}); // length < 5 -> malformed

        Mockito.when(orderItemRepository.findMostOrderedItemInLast30DaysNative(
                        Mockito.eq(restaurantId), Mockito.any()))
                .thenReturn(rows);

        Optional<MostOrderedItemResponse> result =
                orderService.getMostOrderedItemInLast30DaysFromDb(restaurantId);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getMostOrderedItemInLast30DaysFromDb_returnsMappedResponse_whenRowValid() {
        UUID restaurantId = UUID.randomUUID();
        UUID menuItemId = UUID.randomUUID();

        Object[] row = new Object[]{
                menuItemId,
                "Pizza",
                12.5,
                "Cheese Pizza",
                7L
        };
        List<Object[]> rows = new ArrayList<>();
        rows.add(row);

        Mockito.when(orderItemRepository.findMostOrderedItemInLast30DaysNative(
                        Mockito.eq(restaurantId), Mockito.any()))
                .thenReturn(rows);

        Optional<MostOrderedItemResponse> result =
                orderService.getMostOrderedItemInLast30DaysFromDb(restaurantId);

        Assertions.assertTrue(result.isPresent());
        MostOrderedItemResponse response = result.get();
        Assertions.assertEquals(menuItemId, response.getMenuItemId());
        Assertions.assertEquals("Pizza", response.getName());
        Assertions.assertEquals(12.5, response.getPrice());
        Assertions.assertEquals("Cheese Pizza", response.getDescription());
        Assertions.assertEquals(7L, response.getQuantityOrdered());
    }

    // ---------- updateOrder / deleteOrder ----------

    @Test
    void updateOrder_updatesFieldsAndSaves() {
        UUID orderId = UUID.randomUUID();

        Order existingOrder = Order.builder().id(orderId).build();
        existingOrder.setStatus(OrderStatus.ORDERED);
        existingOrder.setTotalAmount(10.0);

        Order updatedDetails = Order.builder().build();
        updatedDetails.setStatus(OrderStatus.DELIVERED);
        updatedDetails.setTotalAmount(25.0);

        Mockito.when(orderRepository.findById(orderId)).thenReturn(existingOrder);
        Mockito.when(orderRepository.save(existingOrder)).thenReturn(existingOrder);

        Order result = orderService.updateOrder(orderId, updatedDetails);

        Assertions.assertSame(existingOrder, result);
        Assertions.assertEquals(OrderStatus.DELIVERED, result.getStatus());
        Assertions.assertEquals(25.0, result.getTotalAmount());
    }

    @Test
    void deleteOrder_loadsAndDeletesOrder() {
        UUID orderId = UUID.randomUUID();
        Order existingOrder = Order.builder().id(orderId).build();

        Mockito.when(orderRepository.findById(orderId)).thenReturn(existingOrder);

        orderService.deleteOrder(orderId);

        Mockito.verify(orderRepository).delete(existingOrder);
    }

    // After all the tests are over - say "Tests Over"
    @AfterAll
    static void afterAll() {
        System.out.println("Tests Over");
    }
}
