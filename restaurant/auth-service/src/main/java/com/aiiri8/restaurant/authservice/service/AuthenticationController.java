package com.aiiri8.restaurant.authservice.service;

import com.aiiri8.restaurant.authservice.service.request.AuthenticationRequest;
import com.aiiri8.restaurant.authservice.service.request.RegisterRequest;
import com.aiiri8.restaurant.authservice.service.response.AuthenticationResponse;
import com.aiiri8.restaurant.authservice.service.response.UserInfoResponse;
import com.aiiri8.restaurant.authservice.user.Role;

import jakarta.servlet.http.HttpServletRequest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant/user-servise")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @GetMapping("/user-info")
  public ResponseEntity<UserInfoResponse> getUserInfo(@NonNull HttpServletRequest request) {
    return ResponseEntity.ok(service.getInfo(request));
  }

  @PostMapping("/set-role")
  public ResponseEntity<String> setRole (@NonNull HttpServletRequest request, @RequestBody Role role) {
    service.setNewRole(request, role);
    return ResponseEntity.ok("New role - " + role.toString());
  }
}
