package com.aiiri8.restaurant.authservice.service;

import com.aiiri8.restaurant.authservice.config.JwtService;
import com.aiiri8.restaurant.authservice.service.request.AuthenticationRequest;
import com.aiiri8.restaurant.authservice.service.request.RegisterRequest;
import com.aiiri8.restaurant.authservice.service.response.AuthenticationResponse;
import com.aiiri8.restaurant.authservice.service.response.UserInfoResponse;
import com.aiiri8.restaurant.authservice.user.Role;
import com.aiiri8.restaurant.authservice.user.User;
import com.aiiri8.restaurant.authservice.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder().username(request.getUsername()).email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword())).role(Role.CUSTOMER)
        .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
    repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    var user = repository.findByEmail(request.getEmail()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public UserInfoResponse getInfo(HttpServletRequest request) {
    final String authHeader = request.getHeader("Authorization");
    String jwt = authHeader.substring(7);
    String userEmail = jwtService.extractEmail(jwt);
    User user = repository.findByEmail(userEmail).get();
    return UserInfoResponse.builder().role(user.getRole()).username(user.getUsername())
        .email(userEmail).build();
  }

  public void setNewRole(HttpServletRequest request, Role role) {
    final String authHeader = request.getHeader("Authorization");
    String jwt = authHeader.substring(7);
    String userEmail = jwtService.extractEmail(jwt);
    Optional<User> user = repository.findByEmail(userEmail);
    user.get().setRole(role);
    repository.save(user.get());
  }
}
