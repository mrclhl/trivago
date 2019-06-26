package com.trivago.booking.spring

import com.trivago.booking.api.exceptions.DateFormatException
import com.trivago.booking.api.exceptions.InvalidDateRangeException
import com.trivago.booking.api.exceptions.InvalidEmailException
import com.trivago.booking.api.exceptions.InvalidNameException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(DateFormatException::class)
    fun wrongDateFormatHandler(exception: DateFormatException): ResponseEntity<*> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidDateRangeException::class)
    fun invalidDateRangeHandler(exception: InvalidDateRangeException): ResponseEntity<*> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidNameException::class)
    fun invalidNameHandler(exception: InvalidNameException): ResponseEntity<*> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(InvalidEmailException::class)
    fun invalidEmailHandler(exception: InvalidEmailException): ResponseEntity<*> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }
}