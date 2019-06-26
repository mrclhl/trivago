package com.trivago.booking.api.exceptions

data class InvalidDateRangeException(override val message: String) : BaseException()