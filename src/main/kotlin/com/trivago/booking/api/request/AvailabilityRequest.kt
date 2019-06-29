package com.trivago.booking.api.request

class AvailabilityRequest : BaseRequest() {

    var occupancy: Array<Occupancy>? = arrayOf()
}