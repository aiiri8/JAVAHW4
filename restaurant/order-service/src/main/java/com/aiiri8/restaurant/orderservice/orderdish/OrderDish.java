package com.aiiri8.restaurant.orderservice.orderdish;

import com.aiiri8.restaurant.orderservice.dish.Dish;
import com.aiiri8.restaurant.orderservice.order.entities.Order;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_order_dish")
public class OrderDish {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dish_id")
  private Dish dish;

  private Integer quantity;
  private BigDecimal price;
}