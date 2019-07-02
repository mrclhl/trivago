package com.trivago.booking.validator

import com.trivago.booking.exceptions.InvalidNameException

class NameValidator {

    companion object {
        // very basic and not too restricting pattern for names...difficult topic, though
        private const val NAME_REGEX = "^\\p{L}(\\s?[\\p{L}\\d.'-])*$"

        fun isNameValid(name: String): Boolean {
            val isValid = NAME_REGEX.toRegex().matches(name)

            if (!isValid) throw InvalidNameException("Invalid name entered.")

            return isValid
        }
    }
}