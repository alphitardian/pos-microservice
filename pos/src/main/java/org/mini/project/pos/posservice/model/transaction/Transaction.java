package org.mini.project.pos.posservice.model.transaction;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.mini.project.pos.posservice.model.Sales;

@Getter
@Setter
@Builder
public class Transaction {

  private TransactionProduct product;

  private Sales sales;

  private TransactionCustomer customer;

  private long productId;

  private long customerId;

  private int quantity;
}

