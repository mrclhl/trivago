package com.trivago.booking.api.request

import com.trivago.booking.model.HotelGuests

class AvailabilityRequest : BaseRequest() {

    var occupancy: Array<HotelGuests>? = arrayOf()
}