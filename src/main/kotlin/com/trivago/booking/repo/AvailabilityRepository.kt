package com.trivago.booking.repo

import com.trivago.booking.model.Room
import com.trivago.booking.model.RoomGuests
import java.time.LocalDate

interface AvailabilityRepository {

    fun retrieveAvailableRoomTypes(startDate: LocalDate, endDate: LocalDate): List<Room>

    fun retrieveAvailableRoomTypesByOccupancy(startDate: LocalDate, endDate: LocalDate, roomGuests: RoomGuests): List<Room>

    fun retrieveAvailableRoom(startDate: LocalDate, endDate: LocalDate, roomTypeCode: String, roomGuests: RoomGuests): Room?
}