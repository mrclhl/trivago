package com.trivago.booking.service

import com.trivago.booking.api.request.AvailabilityRequest
import com.trivago.booking.model.HotelGuests
import com.trivago.booking.model.Room
import java.time.LocalDate

interface AvailabilityService {

    fun retrieveAvailableRoomTypes(availabilityRequest: AvailabilityRequest): List<Room>

    fun retrieveAvailableRoom(startDate: LocalDate, endDate: LocalDate, roomTypeCode: String, occupancy: HotelGuests): Room?
}