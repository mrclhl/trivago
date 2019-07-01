package com.trivago.booking.exceptions

data class InvalidEmailException(override val message: String) : BaseException()