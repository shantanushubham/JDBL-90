package org.geeksforgeeks.food_ordering_app.mapper;

import org.geeksforgeeks.food_ordering_app.dto.response.OrderCreateResponse;
import org.geeksforgeeks.food_ordering_app.dto.response.OrderResponseItem;
import org.geeksforgeeks.food_ordering_app.entities.Order;
import org.geeksforgeeks.food_ordering_app.entities.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderCreateResponse toOrderCreateResponse(Order order) {
        if (order == null) {
            return null;
        }

        OrderCreateResponse response = new OrderCreateResponse();
        response.setOrderId(order.getId());
        response.setOrderStatus(order.getStatus());
        response.setCreatedDate(order.getOrderDate());
        response.setRestaurantName(order.getRestaurant() != null ? order.getRestaurant().getName() : null);
        response.setCustomerName(order.getCustomer() != null 
                ? order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName() 
                : null);
        response.setOrderTotal(order.getTotalAmount() != null ? order.getTotalAmount() : 0.0);
        response.setAddress(order.getDeliveryAddress());
        
        // Map order items
        List<OrderResponseItem> orderItemResponses = order.getOrderItems().stream()
                .map(this::toOrderResponseItem)
                .collect(Collectors.toList());
        response.setItems(orderItemResponses);

        return response;
    }

    private OrderResponseItem toOrderResponseItem(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderResponseItem response = new OrderResponseItem();
        response.setItemName(orderItem.getMenuItem() != null ? orderItem.getMenuItem().getName() : null);
        response.setQuantity(orderItem.getQuantity() != null ? orderItem.getQuantity() : 0);
        
        // Calculate amount (subtotal)
        if (orderItem.getMenuItem() != null && orderItem.getQuantity() != null) {
            response.setAmount(orderItem.getMenuItem().getPrice() * orderItem.getQuantity());
        } else {
            response.setAmount(0.0);
        }

        return response;
    }

}
