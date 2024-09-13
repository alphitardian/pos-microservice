package org.mini.project.pos.posservice.model.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CustomerBalance {
  private Long balance;
}
