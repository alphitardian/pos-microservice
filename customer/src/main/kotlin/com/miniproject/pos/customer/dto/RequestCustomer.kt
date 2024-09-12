package com.miniproject.pos.customer.dto

import com.miniproject.pos.customer.model.PaymentType

data class RequestCustomer (
    val name: String?,
    val username: String?,
    val password: String?,
    val paymentType: MutableList<PaymentType>?,
)