package com.aiiri8.restaurant.orderservice.dish.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddingDishRequest {
  private String name;
  private String description;
  private BigDecimal price;
  private int quantity;
}