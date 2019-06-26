package com.trivago.booking.api.request

import com.trivago.booking.api.exceptions.InvalidEmailException
import javax.naming.InvalidNameException

class ReservationRequest : BaseRequest() {

    var customerFullName: String = ""
    var customerMail: String = ""
    var roomTypes: Array<RoomType> = arrayOf()

    fun validateInput(): Boolean {
        areDatesValid()
        NameValidator.isNameValid(customerFullName)
        EmailValidator.isEmailValid(customerMail)

        return true
    }

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
}