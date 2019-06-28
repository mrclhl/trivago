package com.trivago.booking.model

import java.time.LocalDate

data class Reservation(val startDate: LocalDate, val endDate: LocalDate, val roomId: Int)
