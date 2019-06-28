package com.trivago.booking.service

import com.trivago.booking.api.request.AvailabilityRequest
import com.trivago.booking.model.HotelGuests
import com.trivago.booking.model.Room
import com.trivago.booking.repo.AvailabilityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AvailabilityServiceImpl : AvailabilityService {

    @Autowired
    private lateinit var availabilityRepository: AvailabilityRepository

    override fun retrieveAvailableRoomTypes(availabilityRequest: AvailabilityRequest): List<Room> {
        val startDate = LocalDate.parse(availabilityRequest.startDate)
        val endDate = LocalDate.parse(availabilityRequest.endDate)
        val availableRoomTypes = availabilityRepository.retrieveAvailableRoomTypes(startDate, endDate)

        return availableRoomTypes
    }

    override fun roomTypeAvailable(startDate: String, endDate: String, roomTypeCode: String, occupancy: HotelGuests): Boolean {
        val parsedStartDate = LocalDate.parse(startDate)
        val parsedEndDate = LocalDate.parse(endDate)
        return availabilityRepository.roomTypeAvailable(parsedStartDate, parsedEndDate, roomTypeCode, occupancy)
    }
}