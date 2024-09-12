package com.miniproject.pos.customer.repository

import com.miniproject.pos.customer.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.logging.Logger

@Repository
interface CustomerRepository : JpaRepository<Customer, Long>{

    @Query("SELECT c FROM Customer c WHERE c.id = :id")
     fun findCustomerById(@Param("id") id:Long): Customer?
//     fun findCusById(id:Long): Customer?
}