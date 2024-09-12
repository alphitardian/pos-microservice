package org.mini.project.pos.posservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Customer {

  private long id;

  private String name;

  private String username;

  private long orderId;
}
