package com.trivago.booking.api.request

import com.trivago.booking.api.exceptions.WrongReferenceFormatException


class ReferenceRequest {

    var reference: String = ""

    fun validateInput(): Boolean {
        val isValid = reference.isNotBlank() && reference.length == 6

        if (!isValid) throw WrongReferenceFormatException("The reference is not in the correct format. Valid example: 'A1B2C3'")

        return isValid
    }
}