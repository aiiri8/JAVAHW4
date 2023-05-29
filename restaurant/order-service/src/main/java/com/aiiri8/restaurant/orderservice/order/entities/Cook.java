package com.aiiri8.restaurant.orderservice.order.entities;

import com.aiiri8.restaurant.orderservice.order.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Cook implements Runnable {
  OrderRepository orderRepository;
  Order order;

  public Cook(Order order, OrderRepository orderRepository) {
    this.order = order;
    this.orderRepository = orderRepository;
  }

  @SneakyThrows
  @Override
  public void run() {
    Thread.sleep(3000);
    order.setStatus("finished");
    order.setUpdatedAt(LocalDateTime.now());
    orderRepository.save(order);
  }
}