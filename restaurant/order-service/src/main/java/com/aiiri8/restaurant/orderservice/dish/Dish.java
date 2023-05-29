package com.aiiri8.restaurant.orderservice.dish;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_dish")
public class Dish {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotEmpty
  private String name;

  private String description;

  private BigDecimal price;

  private int quantity;

  @Column(name = "is_available")
  private boolean available;

  @Column(name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at")
  private LocalDateTime updatedAt = LocalDateTime.now();
}