package org.mini.project.pos.posservice.model.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.mini.project.pos.posservice.model.PaymentType;

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

  private Long balance;
}
