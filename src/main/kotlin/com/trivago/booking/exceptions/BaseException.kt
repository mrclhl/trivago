package com.trivago.booking.exceptions

open class BaseException(message: String?) : RuntimeException(message) {

    constructor(): this(null)
}
