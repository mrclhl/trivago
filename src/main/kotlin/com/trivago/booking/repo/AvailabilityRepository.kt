package com.trivago.booking.repo

import com.trivago.booking.model.HotelGuests
import com.trivago.booking.model.Room
import java.time.LocalDate

interface AvailabilityRepository {

    fun retrieveAvailableRoomTypes(startDate: LocalDate, endDate: LocalDate): List<Room>

    fun retrieveAvailableRoom(startDate: LocalDate, endDate: LocalDate, roomTypeCode: String, occupancy: HotelGuests): Room?
}