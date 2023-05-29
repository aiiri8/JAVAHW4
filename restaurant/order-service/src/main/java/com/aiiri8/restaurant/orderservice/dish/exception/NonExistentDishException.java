package com.aiiri8.restaurant.orderservice.dish.exception;

public class NonExistentDishException extends RuntimeException {
  public NonExistentDishException(String message) {
    super(message);
  }
}