package com.trivago.booking.exceptions

data class BookingDoesNotExistException(override val message: String) : BaseException()