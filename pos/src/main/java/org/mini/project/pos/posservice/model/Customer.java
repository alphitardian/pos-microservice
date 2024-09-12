package org.mini.project.pos.posservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class Customer {

  private long id;

  private String name;

  private String username;

  private String password;

  private List<PaymentType> paymentTypes;

  private List<Long> orderId;
}
