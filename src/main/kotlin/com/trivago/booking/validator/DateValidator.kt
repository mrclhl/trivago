package com.trivago.booking.validator

import com.trivago.booking.exceptions.DateFormatException
import com.trivago.booking.exceptions.InvalidDateRangeException
import com.trivago.booking.service.TimeService
import java.time.LocalDate
import java.time.format.DateTimeParseException

class DateValidator {

    companion object {
        fun areDatesValid(startDate: String, endDate: String, timeService: TimeService): Boolean {
            try {
                val parsedStartDate = LocalDate.parse(startDate)
                val parsedEndDate = LocalDate.parse(endDate)
                val today = timeService.retrieveCurrentDate()

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
}