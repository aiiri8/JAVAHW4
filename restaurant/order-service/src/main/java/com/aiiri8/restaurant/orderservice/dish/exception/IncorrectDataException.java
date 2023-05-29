package com.aiiri8.restaurant.orderservice.dish.exception;

public class IncorrectDataException extends RuntimeException {
  public IncorrectDataException(String message) {
    super(message);
  }
}