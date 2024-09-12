package org.mini.project.pos.posservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Product {

    private long id;

    private String productName;

    private String description;

    private int quantity;

    private int price;
}
