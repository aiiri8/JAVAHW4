package com.aiiri8.restaurant.orderservice.order;

import com.aiiri8.restaurant.orderservice.config.JwtService;
import com.aiiri8.restaurant.orderservice.dish.DishRepository;
import com.aiiri8.restaurant.orderservice.order.entities.Cook;
import com.aiiri8.restaurant.orderservice.order.entities.Order;
import com.aiiri8.restaurant.orderservice.order.exception.UnavailableDishException;
import com.aiiri8.restaurant.orderservice.orderdish.OrderDish;
import com.aiiri8.restaurant.orderservice.orderdish.OrderDishRepository;
import com.aiiri8.restaurant.orderservice.user.User;
import com.aiiri8.restaurant.orderservice.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final JwtService jwtService;
  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final OrderRepository orderRepository;

  private final DishRepository dishRepository;

  private final OrderDishRepository orderDishRepository;

  public void createOrder(HttpServletRequest request, OrderRequest orderRequest) {
    final String authHeader = request.getHeader("Authorization");
    String jwt = authHeader.substring(7);
    Optional<User> user = userRepository.findByEmail(jwtService.extractEmail(jwt));

    var dishes = orderRequest.getDishNames();
    for (var curr : dishes) {
      if (dishRepository.findDishByName(curr).isEmpty() || !dishRepository.findDishByName(
          curr).get().isAvailable()) {
        throw new UnavailableDishException("Одно из блюд недоступно для заказа!");
      }
    }

    Order order = Order.builder().user(user.get()).status("cooking")
        .specialRequests(orderRequest.getSpecialRequests()).createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now()).build();
    orderRepository.save(order);

    for (var curr : dishes) {
      var dish = dishRepository.findDishByName(curr).get();
      dish.setQuantity(dish.getQuantity() - 1);
      OrderDish orderDish = OrderDish.builder().order(order).dish(dish).price(dish.getPrice())
          .quantity(dish.getQuantity()).build();
      dishRepository.save(dish);
      orderDishRepository.save(orderDish);
    }

    // Повара-анонимы в неограниченном количестве:D

    Thread thread = new Thread(new Cook(order, orderRepository));
    thread.start();
  }

  public OrderResponse getById(int id) {
    Order order = orderRepository.getReferenceById(id);
    return OrderResponse.builder().specialRequests(order.getSpecialRequests())
        .status(order.getStatus()).build();
  }
}
