package com.trivago.booking.service

import com.trivago.booking.api.request.AvailabilityRequest
import com.trivago.booking.model.HotelGuests
import com.trivago.booking.model.Room
import org.springframework.stereotype.Service

@Service
class AvailabilityServiceImpl : AvailabilityService {

    override fun retrieveAvailableRoomTypes(availabilityRequest: AvailabilityRequest): List<Room> {
        val room = Room("DBL", "Double", HotelGuests(availabilityRequest.occupancy!![0].adults), 200.0)
        return listOf(room)
    }
}