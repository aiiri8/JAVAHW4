package com.aiiri8.restaurant.orderservice.order;

import com.aiiri8.restaurant.orderservice.order.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}