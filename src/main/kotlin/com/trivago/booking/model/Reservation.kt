package com.trivago.booking.model

import java.time.LocalDate

data class Reservation(val startDate: LocalDate, val endDate: LocalDate, val adults: Int?, val juniors: Int?, val babies: Int?, val roomId: Int)
