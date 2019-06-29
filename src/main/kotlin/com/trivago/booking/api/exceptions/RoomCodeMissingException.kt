package com.trivago.booking.api.exceptions

data class RoomCodeMissingException(override val message: String) : BaseException()