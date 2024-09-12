package com.miniproject.pos.customer.util

import com.miniproject.pos.customer.dto.ResponseDto
import org.springframework.http.HttpStatus

fun responseSuccess(message: String): ResponseDto{
    val responseData = ResponseDto(
        responseCode = HttpStatus.OK.value().toString(),
        responseMessage = "Successfully Updated ID $message"
    )
    return responseData
}


