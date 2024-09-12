package com.miniproject.pos.customer.controller

import com.miniproject.pos.customer.dto.RequestCustomer
import com.miniproject.pos.customer.dto.ResponseDto
import com.miniproject.pos.customer.model.Customer
import com.miniproject.pos.customer.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController @Autowired constructor(private val customerService: CustomerService) {

    @GetMapping("/all")
    fun getAllCustomers(): List<Customer> = customerService.findAll()

    @GetMapping("/{id}")
    fun getCustomerById(@PathVariable id: Long): Customer? = customerService.findById(id)

    @PostMapping("/create")
    fun createCustomer(@RequestBody customer: RequestCustomer): Customer = customerService.save(customer)

    @PutMapping("/update/{id}")
    fun updateCustomer(@PathVariable id: Long, @RequestBody customer: Customer): ResponseEntity<ResponseDto> {
        val res = customerService.updateById(id,customer)
        return ResponseEntity.ok(res)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteCustomer(@PathVariable id: Long) = customerService.deleteById(id)
}