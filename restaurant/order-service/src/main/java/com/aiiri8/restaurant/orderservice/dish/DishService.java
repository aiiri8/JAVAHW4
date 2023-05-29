package com.aiiri8.restaurant.orderservice.dish;

import com.aiiri8.restaurant.orderservice.config.JwtService;
import com.aiiri8.restaurant.orderservice.dish.exception.AccessException;
import com.aiiri8.restaurant.orderservice.dish.exception.RepeatedDishException;
import com.aiiri8.restaurant.orderservice.dish.exception.IncorrectDataException;
import com.aiiri8.restaurant.orderservice.dish.exception.NonExistentDishException;
import com.aiiri8.restaurant.orderservice.dish.request.AddingDishRequest;
import com.aiiri8.restaurant.orderservice.dish.request.EditingDishRequest;
import com.aiiri8.restaurant.orderservice.dish.response.DishResponse;
import com.aiiri8.restaurant.orderservice.dish.response.MenuResponse;
import com.aiiri8.restaurant.orderservice.user.Role;
import com.aiiri8.restaurant.orderservice.user.User;
import com.aiiri8.restaurant.orderservice.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DishService {

  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final DishRepository dishRepository;

  public void addNewDish(HttpServletRequest request, AddingDishRequest addingDishRequest) {
    final String authHeader = request.getHeader("Authorization");
    String jwt = authHeader.substring(7);
    String userEmail = jwtService.extractEmail(jwt);
    Optional<User> user = userRepository.findByEmail(userEmail);

    if (user.get().getRole() == Role.CUSTOMER) {
      throw new AccessException("Недостаточный уровень доступа!");
    }
    if (dishRepository.findDishByName(addingDishRequest.getName()).isPresent()) {
      throw new RepeatedDishException("Блюдо с таким названием уже есть!");
    }
    if (addingDishRequest.getPrice().doubleValue() <= 0 || addingDishRequest.getQuantity() <= 0) {
      throw new IncorrectDataException("Цена или количество товаров");
    }

    Dish dish = Dish.builder().name(addingDishRequest.getName()).price(addingDishRequest.getPrice())
        .description(addingDishRequest.getDescription()).quantity(addingDishRequest.getQuantity())
        .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).available(true).build();
    dishRepository.save(dish);
  }

  public void increaseDishQuantity(HttpServletRequest request,
      EditingDishRequest editingDishRequest) {
    final String authHeader = request.getHeader("Authorization");
    String jwt = authHeader.substring(7);
    String userEmail = jwtService.extractEmail(jwt);
    Optional<User> user = userRepository.findByEmail(userEmail);

    if (user.get().getRole() == Role.CUSTOMER) {
      throw new AccessException("Недостаточный уровень доступа!");
    }
    Optional<Dish> dish = dishRepository.findDishByName(editingDishRequest.getName());
    if (dish.isEmpty()) {
      throw new NonExistentDishException("Блюдо не обнаружено!");
    }
    if (editingDishRequest.getQuantity() <= 0) {
      throw new IncorrectDataException("Были введены ошибочные данные!");
    }

    int quantity = dish.get().getQuantity();
    dish.get().setQuantity(quantity + editingDishRequest.getQuantity());
    dishRepository.save(dish.get());
  }

  public MenuResponse getMenu() {
    var dishes = dishRepository.findAll();
    List<DishResponse> responseList = new ArrayList<>();
    for (var dish : dishes) {
      if (dish.isAvailable()) {
        responseList.add(DishResponse.builder().name(dish.getName()).description(dish.getDescription())
            .price(dish.getPrice()).quantity(dish.getQuantity()).build());
      }
    }
    return MenuResponse.builder().menu(responseList).build();
  }
}