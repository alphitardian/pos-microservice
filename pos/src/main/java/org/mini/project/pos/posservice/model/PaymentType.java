package org.mini.project.pos.posservice.model;

public enum PaymentType {
    DEBIT(1),
    KREDIT(2),
    QRIS(3),
    CASH(4),
    PAYLATER(5),
    UNKNOWN(0);

    public final Integer value;

    PaymentType(int value) {
        this.value = value;
    }
}
