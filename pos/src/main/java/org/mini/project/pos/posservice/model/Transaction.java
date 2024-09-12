package org.mini.project.pos.posservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Transaction {

  private Product product;

  private Sales sales;

  private Customer customer;

  private long productId;

  private long customerId;

  private int quantity;
}
