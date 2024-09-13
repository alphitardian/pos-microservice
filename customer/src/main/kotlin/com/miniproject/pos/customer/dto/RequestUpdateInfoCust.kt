package com.miniproject.pos.customer.dto

import com.miniproject.pos.customer.model.PaymentType

data class RequestUpdateInfoCust (
    val name: String?,
    val username: String?,
    val password: String?,
    val paymentType: MutableList<PaymentType>?,
    val orderId: MutableList<Long>?
)