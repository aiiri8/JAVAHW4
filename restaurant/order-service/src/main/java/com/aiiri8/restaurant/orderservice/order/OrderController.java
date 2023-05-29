package com.aiiri8.restaurant.orderservice.order;

import com.aiiri8.restaurant.orderservice.order.exception.UnavailableDishException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant/order-servise/order")
public class OrderController {
  @Autowired
  OrderService orderService;

  @PostMapping("/add-order")
  public ResponseEntity<String> createOrder (@NonNull HttpServletRequest request, @RequestBody OrderRequest orderRequest) {
    try {
      orderService.createOrder(request, orderRequest);
      return ResponseEntity.ok("Заказ сформирован!");
    } catch (UnavailableDishException e) {
      return ResponseEntity.badRequest().body("Некоторые блюда не доступны для заказа:(");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Unknown mistake");
    }
  }

  @GetMapping("/order-info")
  public ResponseEntity<?> getOrder (@NonNull HttpServletRequest request, @RequestParam int id) {
    try {
      orderService.getById(id);
      return ResponseEntity.ok(orderService.getById(id));
    } catch (EntityNotFoundException e) {
      return ResponseEntity.badRequest().body("Введен некорректный ID.");
    } catch (UnavailableDishException e) {
      return ResponseEntity.badRequest().body("Некоторые блюда не доступны для заказа:(");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Unknown mistake");
    }
  }
}
