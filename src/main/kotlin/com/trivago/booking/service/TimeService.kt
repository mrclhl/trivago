package com.trivago.booking.service

import java.time.LocalDate

interface TimeService {

    fun retrieveCurrentDate(): LocalDate
}