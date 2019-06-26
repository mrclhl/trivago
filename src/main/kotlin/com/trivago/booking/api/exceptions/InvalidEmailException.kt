package com.trivago.booking.api.exceptions

data class InvalidEmailException(override val message: String) : BaseException()