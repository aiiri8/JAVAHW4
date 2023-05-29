package com.aiiri8.restaurant.orderservice.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
  private String specialRequests;
  private List<String> dishNames;
}