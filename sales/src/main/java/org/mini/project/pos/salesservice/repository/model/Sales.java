package org.mini.project.pos.salesservice.repository.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sales")
@Data
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @Column(name = "pos_id")
    private String posId;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "amount")
    private int amount;
}
