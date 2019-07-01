package com.trivago.booking.service

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TimeServiceImpl : TimeService {

    override fun retrieveCurrentDate(): LocalDate {
        return LocalDate.now()
    }
}