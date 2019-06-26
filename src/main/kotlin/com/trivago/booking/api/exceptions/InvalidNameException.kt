package com.trivago.booking.api.exceptions

data class InvalidNameException(override val message: String) : BaseException()