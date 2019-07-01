package com.trivago.booking.exceptions

data class ReservationNotPossibleException(override val message: String) : BaseException()