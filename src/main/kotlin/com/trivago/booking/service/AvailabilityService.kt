package com.trivago.booking.service

import com.trivago.booking.api.request.AvailabilityRequest
import com.trivago.booking.model.HotelGuests
import com.trivago.booking.model.Room

interface AvailabilityService {

    fun retrieveAvailableRoomTypes(availabilityRequest: AvailabilityRequest): List<Room>

    fun roomTypeAvailable(startDate: String, endDate: String, roomTypeCode: String, occupancy: HotelGuests): Boolean
}