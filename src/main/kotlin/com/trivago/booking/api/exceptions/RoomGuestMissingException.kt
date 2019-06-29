package com.trivago.booking.api.exceptions

data class RoomGuestMissingException(override val message: String) : BaseException()