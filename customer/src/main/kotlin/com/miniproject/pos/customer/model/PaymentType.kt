package com.miniproject.pos.customer.model

enum class PaymentType (val value: Int){
    DEBIT(1),
    KREDIT(2),
    QRIS(3),
    CASH(4),
    PAYLATER(5),
    UNKNOWN(0)
}