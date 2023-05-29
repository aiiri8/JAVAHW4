package com.aiiri8.restaurant.orderservice.dish;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Integer> {
  Optional<Dish> findDishByName(String name);
}
