package com.trivago.booking.service

import com.trivago.booking.api.request.AvailabilityRequest
import com.trivago.booking.model.RoomTypes

interface AvailabilityService {

    fun retrieveAvailableRoomTypes(availabilityRequest: AvailabilityRequest): RoomTypes
}