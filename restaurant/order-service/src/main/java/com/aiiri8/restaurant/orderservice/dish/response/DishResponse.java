package com.aiiri8.restaurant.orderservice.dish.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DishResponse {
  String name;
  String description;
  BigDecimal price;
  int quantity;
}