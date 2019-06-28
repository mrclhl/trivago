package com.trivago.booking.api.exceptions

data class ReservationNotPossibleException(override val message: String) : BaseException()