package com.trivago.booking.exceptions

data class RoomGuestMissingException(override val message: String) : BaseException()