package com.trivago.booking.exceptions

data class InvalidNameException(override val message: String) : BaseException()