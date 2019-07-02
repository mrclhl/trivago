package com.trivago.booking.validator

import com.trivago.booking.exceptions.InvalidEmailException

class EmailValidator {

    companion object {
        // from android.util.Patterns
        private const val EMAIL_REGEX = "[a-zA-Z0-9+._%\\-+]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"

        fun isEmailValid(email: String): Boolean {
            val isValid = EMAIL_REGEX.toRegex().matches(email)

            if (!isValid) throw InvalidEmailException("Invalid email entered. Format must be 'example@mail.com'")

            return isValid
        }
    }
}