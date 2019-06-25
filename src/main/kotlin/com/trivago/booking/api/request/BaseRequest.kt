package com.trivago.booking.api.request

import com.trivago.booking.api.exceptions.DateFormatException
import java.time.LocalDate
import java.time.format.DateTimeParseException

open class BaseRequest {

    var startDate: String = ""
    var endDate: String = ""

    fun areDatesValid(): Boolean {
        try {
            LocalDate.parse(startDate)
            LocalDate.parse(endDate)

            return true
        } catch (e: DateTimeParseException) {
            throw DateFormatException("One or both of the dates are not in the correct format. Expected: 'YYYY-MM-DD'")
        }
    }
}