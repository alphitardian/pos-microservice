package com.miniproject.pos.customer.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Customer(

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = 0,
    val name: String?,
    val username: String?,
    val password: String?,
    val paymentType: MutableList<PaymentType>?,
    val orderId: MutableList<Long>? = mutableListOf()

)    {
    constructor() : this(0, "", "", "", mutableListOf(PaymentType.CASH), mutableListOf())
}