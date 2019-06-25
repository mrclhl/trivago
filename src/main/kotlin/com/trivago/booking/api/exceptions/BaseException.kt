package com.trivago.booking.api.exceptions

open class BaseException(override val message: String?) : RuntimeException(message) {

    constructor(): this(null)
}
