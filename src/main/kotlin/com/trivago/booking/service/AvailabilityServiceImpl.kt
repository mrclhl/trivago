package com.trivago.booking.service

import com.trivago.booking.api.request.AvailabilityRequest
import com.trivago.booking.model.Room
import com.trivago.booking.repo.AvailabilityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AvailabilityServiceImpl : AvailabilityService {

    @Autowired
    private lateinit var availabilityRepository: AvailabilityRepository

    override fun retrieveAvailableRoomTypes(availabilityRequest: AvailabilityRequest): List<Room> {
        val availableRoomTypes = availabilityRepository.retrieveAvailableRoomTypes(availabilityRequest.startDate, availabilityRequest.endDate)

        return availableRoomTypes
    }
}