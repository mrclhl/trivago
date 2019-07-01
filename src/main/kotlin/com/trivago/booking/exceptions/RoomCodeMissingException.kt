package com.trivago.booking.exceptions

data class RoomCodeMissingException(override val message: String) : BaseException()