package org.mini.project.pos.posservice.model.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TransactionCustomer {

    private long id;

    private String name;

    private String username;

    private List<Long> orderId;
}
