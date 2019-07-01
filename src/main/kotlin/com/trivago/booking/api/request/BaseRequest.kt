package com.trivago.booking.api.request

import com.trivago.booking.exceptions.DateFormatException
import com.trivago.booking.exceptions.InvalidDateRangeException
import java.time.LocalDate
import java.time.format.DateTimeParseException

open class BaseRequest {

    var startDate: String = ""
    var endDate: String = ""

    fun areDatesValid(): Boolean {
        try {
            val parsedStartDate = LocalDate.parse(startDate)
            val parsedEndDate = LocalDate.parse(endDate)
            val today = LocalDate.now()

            if (parsedEndDate.isBefore(parsedStartDate) || parsedEndDate.isEqual(parsedStartDate))
                throw InvalidDateRangeException("Start date has to be before end date.")

            if (parsedStartDate.isBefore(today))
                throw InvalidDateRangeException("Start date must not be in the past.")

            return true
        } catch (e: DateTimeParseException) {
            throw DateFormatException("One or both of the dates are not in the correct format. Expected: 'YYYY-MM-DD'")
        }
    }
}