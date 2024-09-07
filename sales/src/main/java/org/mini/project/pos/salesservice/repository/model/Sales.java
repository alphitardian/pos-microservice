package org.mini.project.pos.salesservice.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sales")
@Data
public class Sales {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "sales_name")
    private String salesName;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private int quantity;
}
