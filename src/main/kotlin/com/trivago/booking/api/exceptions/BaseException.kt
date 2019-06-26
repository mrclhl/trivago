package com.trivago.booking.api.exceptions

open class BaseException(message: String?) : RuntimeException(message) {

    constructor(): this(null)
}
