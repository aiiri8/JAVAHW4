package com.aiiri8.restaurant.orderservice.dish.exception;

public class RepeatedDishException extends RuntimeException {
  public RepeatedDishException(String message) {
    super(message);
  }
}