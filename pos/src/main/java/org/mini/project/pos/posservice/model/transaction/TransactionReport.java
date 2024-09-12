package org.mini.project.pos.posservice.model.transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "transactions")
public class TransactionReport {

  @Id
  @Column(name = "sales_id")
  private long salesId;

  @Column(name = "product_id")
  private long productId;

  @Column(name = "customer_id")
  private long customerId;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "amount")
  private int amount;

  @Column(name = "customer_name")
  private String customerName;
}
