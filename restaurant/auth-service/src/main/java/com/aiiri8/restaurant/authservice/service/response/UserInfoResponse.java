package com.aiiri8.restaurant.authservice.service.response;

import com.aiiri8.restaurant.authservice.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
  private String username;
  private String email;
  private Role role;
}