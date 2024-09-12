package com.miniproject.pos.customer.service

import com.miniproject.pos.customer.dto.RequestCustomer
import com.miniproject.pos.customer.dto.ResponseDto
import com.miniproject.pos.customer.model.Customer
import com.miniproject.pos.customer.repository.CustomerRepository
import com.miniproject.pos.customer.util.responseSuccess
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.InvalidParameterException
import java.util.logging.Logger

@Service
class CustomerService @Autowired constructor(
    private val customerRepository: CustomerRepository,
    private val logger: Logger,
    private val passwordEncoder: PasswordEncoder
) {

    fun findAll(): List<Customer> {
        val listCustomer = customerRepository.findAll()
        logger.info("List Customer: $listCustomer")
        if(listCustomer.isEmpty()){
            logger.info("The list Customer is Empty")
            throw NotFoundException()
        }

        return listCustomer
    }


    fun findById(id: Long): Customer? {
       val customer = customerRepository.findCustomerById(id) ?: throw NotFoundException()

        logger.info("Found Customer $id : $customer")
        return customer
    }


    fun save(customer: RequestCustomer): Customer {
        //Encrypt the password
        val hashedPassword = passwordEncoder.encode(customer.password)
        logger.info("Password after Hash: $hashedPassword")

        val customerAfterHash = Customer(
            name = customer.name,
            username = customer.username,
            password = hashedPassword,
            paymentType = customer.paymentType,
        )

        val newCustomer = customerRepository.save(customerAfterHash) ?: throw InvalidParameterException()
        logger.info("Successfully Inserted: $newCustomer")
        return newCustomer
    }

    fun deleteById(id: Long): ResponseDto {
        customerRepository.deleteById(id) ?: throw InvalidParameterException()
        logger.info("Delete succesfully $id")
        return responseSuccess("Successfully Deleted ID: $id")

    }

    fun updateById(id:Long, customer: Customer): ResponseDto {
        var updateCustomer = customerRepository.findCustomerById(id)?: throw NotFoundException()
        logger.info("Success get Customer: $customer")

        val newName = customer.name
        val newUsername = customer.username
        val newPassword = customer.password
        val newPaymentType = customer.paymentType

        updateCustomer = Customer(
            id = updateCustomer.id,
            name = newName,
            username = newUsername,
            password = newPassword,
            paymentType = newPaymentType,
            orderId = updateCustomer.orderId
        )

        customerRepository.save(updateCustomer)
        logger.info("Success Update $id with new data $updateCustomer")
        return responseSuccess("Successfully Updated ID: $id")
    }
}