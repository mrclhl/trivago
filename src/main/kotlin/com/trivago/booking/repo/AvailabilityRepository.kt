package com.trivago.booking.repo

import com.trivago.booking.model.Room

interface AvailabilityRepository {

    fun retrieveAvailableRoomTypes(startDate: String, endDate: String): List<Room>
}