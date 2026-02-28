package org.geeksforgeeks.food_ordering_app.service;

import org.geeksforgeeks.food_ordering_app.entities.OrderItem;
import org.geeksforgeeks.food_ordering_app.repository.jpa.OrderItemJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {

    @Mock
    private OrderItemJpaRepository orderItemRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    void createOrderItem_savesAndReturns() {
        OrderItem orderItem = OrderItem.builder().build();
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        OrderItem result = orderItemService.createOrderItem(orderItem);

        assertSame(orderItem, result);
    }

    @Test
    void getOrderItemById_returnsOptional() {
        UUID id = UUID.randomUUID();
        when(orderItemRepository.findById(id)).thenReturn(Optional.empty());

        assertTrue(orderItemService.getOrderItemById(id).isEmpty());
    }

    @Test
    void getAllOrderItems_returnsList() {
        List<OrderItem> list = List.of(OrderItem.builder().build());
        when(orderItemRepository.findAll()).thenReturn(list);

        assertSame(list, orderItemService.getAllOrderItems());
    }

    @Test
    void getOrderItemsByOrderId_returnsList() {
        UUID orderId = UUID.randomUUID();
        List<OrderItem> list = List.of();
        when(orderItemRepository.findByOrderId(orderId)).thenReturn(list);

        assertSame(list, orderItemService.getOrderItemsByOrderId(orderId));
    }

    @Test
    void updateOrderItem_updatesAndSaves() {
        UUID id = UUID.randomUUID();
        OrderItem existing = OrderItem.builder().build();
        OrderItem details = OrderItem.builder().quantity(5).build();
        when(orderItemRepository.findById(id)).thenReturn(Optional.of(existing));
        when(orderItemRepository.save(existing)).thenReturn(existing);

        OrderItem result = orderItemService.updateOrderItem(id, details);

        assertSame(existing, result);
        verify(orderItemRepository).save(existing);
    }

    @Test
    void updateOrderItem_throwsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(orderItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderItemService.updateOrderItem(id, OrderItem.builder().build()));
    }

    @Test
    void deleteOrderItem_findsAndDeletes() {
        UUID id = UUID.randomUUID();
        OrderItem orderItem = OrderItem.builder().build();
        when(orderItemRepository.findById(id)).thenReturn(Optional.of(orderItem));

        orderItemService.deleteOrderItem(id);

        verify(orderItemRepository).delete(orderItem);
    }

    @Test
    void deleteOrderItem_throwsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(orderItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderItemService.deleteOrderItem(id));
    }
}
