package com.trivago.booking.service

import com.trivago.booking.api.request.AvailabilityRequest
import com.trivago.booking.api.response.BaseResponse

interface AvailabilityService {

    fun retrieveAvailableRoomTypes(availabilityRequest: AvailabilityRequest): BaseResponse
}