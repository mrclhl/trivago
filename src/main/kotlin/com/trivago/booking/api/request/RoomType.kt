package com.trivago.booking.api.request

import com.trivago.booking.model.RoomGuests

class RoomType {

    var roomTypeCode: String = ""
    var occupancy: RoomGuests = RoomGuests(null, null, null)
}