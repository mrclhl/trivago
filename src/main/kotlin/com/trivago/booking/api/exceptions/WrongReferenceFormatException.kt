package com.trivago.booking.api.exceptions

data class WrongReferenceFormatException(override val message: String) : BaseException()