package org.mini.project.pos.posservice.model.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionProduct {

    private long id;

    private String productName;

    private String description;

    private int price;
}
