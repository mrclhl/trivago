package com.trivago.booking.api.request

import com.trivago.booking.service.TimeService
import com.trivago.booking.validator.DateValidator
import com.trivago.booking.validator.EmailValidator
import com.trivago.booking.validator.NameValidator
import com.trivago.booking.validator.RoomTypeValidator


class ReservationRequest : BaseRequest() {

    var customerFullName: String = ""
    var customerMail: String = ""
    var roomTypes: Array<RoomType> = arrayOf()

    fun validateInput(timeService: TimeService): Boolean {
        DateValidator.areDatesValid(startDate, endDate, timeService)
        NameValidator.isNameValid(customerFullName)
        EmailValidator.isEmailValid(customerMail)
        RoomTypeValidator.isRequestValid(roomTypes)

        return true
    }
}