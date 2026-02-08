package org.geeksforgeeks.my_spring_app.repository;

import org.aspectj.weaver.ast.Or;
import org.geeksforgeeks.my_spring_app.entities.Order;
import org.geeksforgeeks.my_spring_app.exceptions.NotFoundException;
import org.geeksforgeeks.my_spring_app.repository.jpa.OrderJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Autowired
    public OrderRepository(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    public Order getOrderById(UUID id) {
        return this.orderJpaRepository.findById(id).orElseThrow(() ->
                new NotFoundException(Order.class, "id", id));
    }

    public Order saveOrder(Order order) {
        return this.orderJpaRepository.save(order);
    }

    public List<Order> getOrdersByUser(UUID userId) {
        return this.orderJpaRepository.findByUser_Id(userId);
    }

    public void deleteOrder(UUID id) {
        this.orderJpaRepository.deleteById(id);
    }

    public List<Order> getAllOrders() {
        return this.orderJpaRepository.findAll();
    }
}
