package com.aiiri8.restaurant.orderservice.dish;

import com.aiiri8.restaurant.orderservice.dish.exception.AccessException;
import com.aiiri8.restaurant.orderservice.dish.exception.RepeatedDishException;
import com.aiiri8.restaurant.orderservice.dish.exception.IncorrectDataException;
import com.aiiri8.restaurant.orderservice.dish.exception.NonExistentDishException;
import com.aiiri8.restaurant.orderservice.dish.request.AddingDishRequest;
import com.aiiri8.restaurant.orderservice.dish.request.EditingDishRequest;

import jakarta.servlet.http.HttpServletRequest;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant/order-servise/dish")
public class DishController {

  @Autowired
  DishService dishService;

  @PostMapping("/add-dish")
  public ResponseEntity<String> addNewDish(@NonNull HttpServletRequest request,
      @RequestBody AddingDishRequest newDishRequest) {
    try {
      dishService.addNewDish(request, newDishRequest);
      return ResponseEntity.ok("Блюдо добавлено!");
    } catch (AccessException e) {
      return ResponseEntity.badRequest().body("У пользователя недостаточный уровень доступа!");
    } catch (RepeatedDishException e) {
      return ResponseEntity.badRequest().body("Нельзя создать повтор уже существующего блюда.");
    } catch (IncorrectDataException e) {
      return ResponseEntity.badRequest().body("Обнаружена ошибка в вводе!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Unknown mistake");
    }
  }

  @PostMapping("/increase-quantity")
  public ResponseEntity<String> increaseDishQuantity(@NonNull HttpServletRequest request,
      @RequestBody EditingDishRequest dishQuantityRequest) {
    try {
      dishService.increaseDishQuantity(request, dishQuantityRequest);
      return ResponseEntity.ok("Добавлено блюд: " + dishQuantityRequest.getQuantity());
    } catch (AccessException e) {
      return ResponseEntity.badRequest().body("Ошибка доступа!");
    } catch (NonExistentDishException e) {
      return ResponseEntity.badRequest().body("Введено несуществующее блюдо.");
    } catch (IncorrectDataException e) {
      return ResponseEntity.badRequest().body("Блюдо (название*) некоррентно");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Unknown mistake");
    }
  }

  @GetMapping("/get-menu")
  public ResponseEntity<?> getMenu(@NonNull HttpServletRequest request) {
    try {
      return ResponseEntity.ok(dishService.getMenu());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Unknown mistake");
    }
  }
}