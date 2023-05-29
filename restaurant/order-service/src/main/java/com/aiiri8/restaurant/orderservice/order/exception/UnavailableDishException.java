package com.aiiri8.restaurant.orderservice.order.exception;

public class UnavailableDishException extends RuntimeException {
  public UnavailableDishException(String message) {
    super(message);
  }
}