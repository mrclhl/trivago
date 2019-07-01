package com.trivago.booking.spring

import com.trivago.booking.exceptions.BookingDoesNotExistException
import com.trivago.booking.exceptions.DateFormatException
import com.trivago.booking.exceptions.InvalidDateRangeException
import com.trivago.booking.exceptions.InvalidEmailException
import com.trivago.booking.exceptions.InvalidNameException
import com.trivago.booking.exceptions.ReservationNotPossibleException
import com.trivago.booking.exceptions.WrongReferenceFormatException
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

    @ExceptionHandler(ReservationNotPossibleException::class)
    fun reservationNotPossibleHandler(exception: ReservationNotPossibleException): ResponseEntity<*> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BookingDoesNotExistException::class)
    fun bookingDoesNotExistHandler(exception: BookingDoesNotExistException): ResponseEntity<*> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(WrongReferenceFormatException::class)
    fun wrongReferenceFormatHandler(exception: WrongReferenceFormatException): ResponseEntity<*> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }
}