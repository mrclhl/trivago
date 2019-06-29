package com.trivago.booking.service

import com.trivago.booking.model.Room
import com.trivago.booking.model.RoomGuests
import java.time.LocalDate

interface AvailabilityService {

    fun retrieveAvailableRoomTypes(startDate: String, endDate: String, roomGuests: List<RoomGuests>?): List<Room>

    fun retrieveAvailableRoom(startDate: LocalDate, endDate: LocalDate, roomTypeCode: String, roomGuests: RoomGuests): Room?
}