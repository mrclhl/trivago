package com.trivago.booking.service

import com.trivago.booking.api.request.AvailabilityRequest
import com.trivago.booking.api.response.BaseResponse
import com.trivago.booking.repo.AvailabilityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AvailabilityServiceImpl : AvailabilityService {

    @Autowired
    private lateinit var availabilityRepository: AvailabilityRepository

    override fun retrieveAvailableRoomTypes(availabilityRequest: AvailabilityRequest): BaseResponse {
        val availableRoomTypes = availabilityRepository.retrieveAvailableRoomTypes(availabilityRequest.startDate, availabilityRequest.endDate)

        return BaseResponse(availabilityRequest.startDate, availabilityRequest.endDate, availableRoomTypes)
    }
}