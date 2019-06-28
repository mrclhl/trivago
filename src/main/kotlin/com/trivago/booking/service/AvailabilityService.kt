package com.trivago.booking.service

import com.trivago.booking.api.request.AvailabilityRequest
import com.trivago.booking.model.Room

interface AvailabilityService {

    fun retrieveAvailableRoomTypes(availabilityRequest: AvailabilityRequest): List<Room>
}