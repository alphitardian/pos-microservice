package org.mini.project.pos.posservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Sales {

  private long id;

  private String posId;

  private String productId;

  private int quantity;

  private int amount;
}
